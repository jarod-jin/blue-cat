package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.model.dto.OrgRoleDTO;
import cn.jarod.bluecat.auth.repository.OrgRoleRepository;
import cn.jarod.bluecat.auth.service.IOrgRoleService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther jarod.jin 2019/11/4
 */
@Service
public class OrgRoleService implements IOrgRoleService {

    @Autowired
    private OrgRoleRepository orgRoleRepository;

    /**
     * 保存组织角色关系
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public OrgRoleDO saveOrgRole(OrgRoleDTO dto){
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(dto.getOrgCode());
        orgRoleDO.setRoleCode(dto.getRoleCode());
        if (orgRoleRepository.exists(Example.of(orgRoleDO)))
            throw new BaseException(ReturnCode.S401);
        orgRoleDO.setCreator(dto.getOperator());
        orgRoleDO.setModifier(dto.getOperator());
        return orgRoleRepository.save(orgRoleDO);
    }


    /**
     * 删除一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public void delOrgRole(OrgRoleDTO dto) {
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(dto.getOrgCode());
        orgRoleDO.setRoleCode(dto.getRoleCode());
        orgRoleRepository.delete(orgRoleRepository.findOne(Example.of(orgRoleDO)).orElseThrow(()->new BaseException(ReturnCode.D400)));
    }



}
