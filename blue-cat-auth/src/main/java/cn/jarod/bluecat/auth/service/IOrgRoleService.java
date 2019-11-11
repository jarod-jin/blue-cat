package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.model.bo.LinkOrgRoleBO;
import cn.jarod.bluecat.core.model.auth.ReqGrantedAuthority;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/7
 */
public interface IOrgRoleService {

    OrgRoleDO saveOrgRole(LinkOrgRoleBO linkOrgRoleBO);

    void delOrgRole(LinkOrgRoleBO linkOrgRoleBO);

    List<String> queryRoleCodesByOrg(LinkOrgRoleBO linkOrgRoleBO);

    List<ReqGrantedAuthority> queryOrgRoleByIds(List<Long> ids);
}
