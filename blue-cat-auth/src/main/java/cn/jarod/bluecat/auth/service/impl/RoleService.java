package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.dto.RoleDTO;
import cn.jarod.bluecat.auth.repository.RoleRepository;
import cn.jarod.bluecat.auth.service.IRoleService;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @auther jarod.jin 2019/11/4
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

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
}
