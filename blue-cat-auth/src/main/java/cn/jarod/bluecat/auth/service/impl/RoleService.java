package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.dto.OrgRoleDTO;
import cn.jarod.bluecat.auth.model.dto.RoleDTO;
import cn.jarod.bluecat.auth.repository.OrgRoleRepository;
import cn.jarod.bluecat.auth.repository.RoleRepository;
import cn.jarod.bluecat.auth.service.IRoleService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.BaseQO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auther jarod.jin 2019/11/4
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrgRoleRepository orgRoleRepository;

    /**
     * 保存一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public RoleDO saveRole(RoleDTO dto) {
        dto.clearId();
        RoleDO roleDO = roleRepository.findByRoleCode(dto.getRoleCode()).orElse(new RoleDO());
        roleDO.setModifier(dto.getOperator());
        roleDO.setCreator(dto.getOperator());
        BeanHelperUtil.copyNotNullProperties(dto,roleDO);
        return roleRepository.save(roleDO);
    }

    /**
     * 删除一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public void delRole(RoleDTO dto) {
        roleRepository.delete(roleRepository.findByRoleCode(dto.getRoleCode()).orElseThrow(()->new BaseException(ReturnCode.D400)));
    }

    /**
     * 根据Code列表返回对应的散列表
     * @param codes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String,RoleDO> queryRoleMapByCodes(List<String> codes) {
        Map<String,RoleDO> dtoMap = Maps.newHashMap();
        roleRepository.findAllByRoleCodeIn(codes).forEach(e-> dtoMap.put(e.getRoleCode(),e));
        return dtoMap;
    }

    /**
     * 根据条件查询角色Page
     * @param qo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoleDO> queryRolePage(BaseQO qo) {
        Sort sort = new Sort(qo.isASC()? Sort.Direction.ASC:Sort.Direction.DESC, Const.CREATE_DATE);
        Pageable pageable = PageRequest.of(qo.getPageNum() - 1, qo.getPageCount(), sort);
        return roleRepository.findAll(pageable);
    }


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
}
