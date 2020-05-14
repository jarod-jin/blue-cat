package cn.jarod.bluecat.access.role.service;

import cn.jarod.bluecat.access.group.pojo.LinkOrgRoleBO;
import cn.jarod.bluecat.access.role.entity.OrgRoleDO;
import cn.jarod.bluecat.access.role.entity.RoleDO;

import cn.jarod.bluecat.access.role.pojo.CrudRoleBO;
import cn.jarod.bluecat.core.base.model.BaseQuery;
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
     * 通过所属系统编号和角色编码查询角色信息的散列表
     * @param codes 角色编码
     * @param sys 所属系统编号
     * @return Map
     */
    Map<String,RoleDO> findRoleMapByCodes(List<String> codes, String sys);

    /**
     * 分页查询角色信息
     * @param qo 查询对象
     * @return Page
     */
    Page<RoleDO> findRolePage(BaseQuery qo);

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
    List<String> findRoleCodesByOrg(LinkOrgRoleBO linkOrgRoleBO);

    /**
     * 根据Id列表查询用户权限数据组
     * @param ids 组织Id
     * @return List
     */
    List<UserAuthority> findOrgRoleByIds(List<Long> ids);
}

