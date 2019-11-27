package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.LinkOrgRoleBO;
import cn.jarod.bluecat.auth.model.bo.CrudRoleBO;
import cn.jarod.bluecat.core.model.BaseQO;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @auther jarod.jin 2019/11/4
 */
public interface IRoleService {

    RoleDO saveRole(CrudRoleBO dto);

    void delRole(CrudRoleBO dto);

    Map<String,RoleDO> queryRoleMapByCodes(List<String> codes, String sys);

    Page<RoleDO> queryRolePage(BaseQO qo);

    OrgRoleDO saveOrgRole(LinkOrgRoleBO linkOrgRoleBO);

    void delOrgRole(LinkOrgRoleBO linkOrgRoleBO);

    List<String> queryRoleCodesByOrg(LinkOrgRoleBO linkOrgRoleBO);

    List<UserAuthority> queryOrgRoleByIds(List<Long> ids);
}

