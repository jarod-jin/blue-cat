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
 * @author jarod.jin 2019/11/4
 */
public interface RoleService {

    /**
     * 保存角色信息
     * @param roleBO 角色对象
     * @return RoleDO
     */
    RoleDO saveRole(CrudRoleBO roleBO);

    /**
     * 删除角色信息
     * @param roleBO 角色对象
     */
    void delRole(CrudRoleBO roleBO);

    /**
     * 通过系统编码和角色编码查询角色信息的散列表
     * @param codes 角色编码
     * @param sys 系统编码
     * @return Map
     */
    Map<String,RoleDO> queryRoleMapByCodes(List<String> codes, String sys);

    /**
     * 分页查询角色信息
     * @param qo 查询对象
     * @return Page
     */
    Page<RoleDO> queryRolePage(BaseQO qo);

    /**
     * 保存
     * @param linkOrgRoleBO 组织角色对象
     * @return
     */
    OrgRoleDO saveOrgRole(LinkOrgRoleBO linkOrgRoleBO);

    /**
     * 删除组织角色关系
     * @param linkOrgRoleBO 组织角色对象
     */
    void delOrgRole(LinkOrgRoleBO linkOrgRoleBO);

    /**
     * 根据组织查询角色
     * @param linkOrgRoleBO 组织角色对象
     * @return List
     */
    List<String> queryRoleCodesByOrg(LinkOrgRoleBO linkOrgRoleBO);

    /**
     * 根据Id列表查询用户权限数据组
     * @param ids 组织Id
     * @return List
     */
    List<UserAuthority> queryOrgRoleByIds(List<Long> ids);
}

