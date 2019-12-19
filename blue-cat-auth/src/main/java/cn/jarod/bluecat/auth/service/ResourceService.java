package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import cn.jarod.bluecat.auth.model.bo.LinkRoleResourceBO;
import cn.jarod.bluecat.auth.model.bo.QueryResourceTreeBO;
import cn.jarod.bluecat.auth.model.bo.CrudResourceBO;

import java.util.List;

/**
 * @author jarod.jin 2019/11/13
 */
public interface ResourceService {

    /**
     * 资源保存
     * @param resourceBO 资源
     * @return ResourceDO
     */
    ResourceDO saveResource(CrudResourceBO resourceBO);

    /**
     * 资源删除
     * @param resourceBO 资源
     */
    void delResource(CrudResourceBO resourceBO);

    /**
     * 查询资源树
     * @param sys 系统编码
     * @param roleCodes 角色编码列表
     * @return
     */
    List<QueryResourceTreeBO> queryResourceTreeBySysAndRoleCodes(String sys, List<String> roleCodes);

    /**
     * 绑定角色资源
     * @param linkBO 绑定对象
     * @return RoleResourceDO
     */
    RoleResourceDO saveRoleResource(LinkRoleResourceBO linkBO);

    /**
     * 删除资源角色
     * @param linkBO 绑定对象
     */
    void delRoleResource(LinkRoleResourceBO linkBO);

    /**
     * 判断是否角色绑定任何资源
     * @param roleCode 角色编码
     * @return
     */
    boolean hasLinkByRoleCode(String roleCode);
}
