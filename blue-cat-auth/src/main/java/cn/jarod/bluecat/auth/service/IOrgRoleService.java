package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.model.dto.OrgRoleDTO;

/**
 * @auther jarod.jin 2019/11/7
 */
public interface IOrgRoleService {

    OrgRoleDO saveOrgRole(OrgRoleDTO dto);

    void delOrgRole(OrgRoleDTO dto);
}
