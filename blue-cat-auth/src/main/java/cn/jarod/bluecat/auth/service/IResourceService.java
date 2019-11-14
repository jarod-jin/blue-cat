package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import cn.jarod.bluecat.auth.model.bo.LinkRoleResourceBO;
import cn.jarod.bluecat.auth.model.bo.SaveResourceBO;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/13
 */
public interface IResourceService {

    ResourceDO saveResource(SaveResourceBO resourceBO);

    void delResource(SaveResourceBO resourceBO);

    List<ResourceDO> queryResourceListByCodes(List<String> codes, String sys);

    RoleResourceDO saveRoleResource(LinkRoleResourceBO linkBO);

    void delRoleResource(LinkRoleResourceBO linkBO);

    boolean hasLinkByRoleCode(String roleCode);
}
