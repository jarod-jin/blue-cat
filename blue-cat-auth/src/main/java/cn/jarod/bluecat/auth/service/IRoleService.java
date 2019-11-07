package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.dto.OrgRoleDTO;
import cn.jarod.bluecat.auth.model.dto.RoleDTO;
import cn.jarod.bluecat.core.model.BaseQO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @auther jarod.jin 2019/11/4
 */
public interface IRoleService {

    RoleDO saveRole(RoleDTO dto);

    void delRole(RoleDTO dto);

    Map<String,RoleDO> queryRoleMapByCodes(List<String> codes);

    Page<RoleDO> queryRolePage(BaseQO qo);

    OrgRoleDO saveOrgRole(OrgRoleDTO dto);
}
