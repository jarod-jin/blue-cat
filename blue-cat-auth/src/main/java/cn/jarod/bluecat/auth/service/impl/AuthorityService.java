package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.model.dto.OrgRoleDTO;
import cn.jarod.bluecat.auth.model.dto.RoleResourceDTO;
import cn.jarod.bluecat.auth.repository.OrgRoleRepository;
import cn.jarod.bluecat.auth.repository.UserLocationRepository;
import cn.jarod.bluecat.auth.service.IAuthorityService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther jarod.jin 2019/10/10
 */
@Slf4j
@Service
public class AuthorityService implements IAuthorityService {

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private OrgRoleRepository orgRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResourceDTO> findAuthorities(String name) {
        orgRoleRepository.findAllByUsername(name);

        return Lists.newArrayList();
    }

    public List<RoleResourceDTO> saveAuthorities(String name) {
        return Lists.newArrayList();
    }


    /**
     * 保存OrgRole
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrgRoleDO saveOrgRole(OrgRoleDTO dto) {
        OrgRoleDO orgRole = new OrgRoleDO();
        orgRole.setOrgCode(dto.getOrgCode());
        orgRole.setRoleCode(dto.getRoleCode());
        if (orgRoleRepository.exists(Example.of(orgRole)))
            throw new BaseException(ReturnCode.S401);
        orgRole.setModifier(dto.getOperator());
        orgRole.setCreator(dto.getOperator());
        return orgRoleRepository.save(orgRole);
    }

}
