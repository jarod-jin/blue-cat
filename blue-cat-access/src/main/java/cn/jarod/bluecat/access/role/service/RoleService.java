package cn.jarod.bluecat.access.role.service;

import cn.jarod.bluecat.access.role.pojo.entity.RolePO;

import cn.jarod.bluecat.access.role.pojo.CrudRoleBO;
import cn.jarod.bluecat.core.base.model.BaseQuery;
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
    RolePO saveRole(CrudRoleBO roleBO);

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
    Map<String, RolePO> findRoleMapByCodes(List<String> codes, String sys);

    /**
     * 分页查询角色信息
     * @param qo 查询对象
     * @return Page
     */
    Page<RolePO> findRolePage(BaseQuery qo);


//    /**
//     * 根据Id列表查询用户权限数据组
//     * @param ids 组织Id
//     * @return List
//     */
//    List<UserAuthority> findOrgRoleByIds(List<Long> ids);
}

