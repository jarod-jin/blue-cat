package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import cn.jarod.bluecat.auth.model.bo.LinkRoleResourceBO;
import cn.jarod.bluecat.auth.model.bo.QueryResourceTreeBO;
import cn.jarod.bluecat.auth.model.bo.CrudResourceBO;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/13
 */
public interface IResourceService {

    ResourceDO saveResource(CrudResourceBO resourceBO);

    void delResource(CrudResourceBO resourceBO);

    List<QueryResourceTreeBO> queryResourceTreeBySysAndRoleCodes(String sys, List<String> roleCodes);

    RoleResourceDO saveRoleResource(LinkRoleResourceBO linkBO);

    void delRoleResource(LinkRoleResourceBO linkBO);

    boolean hasLinkByRoleCode(String roleCode);
}
