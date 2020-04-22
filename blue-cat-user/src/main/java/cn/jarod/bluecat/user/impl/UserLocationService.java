package cn.jarod.bluecat.user.impl;

import cn.jarod.bluecat.user.entity.UserLocationDO;
import cn.jarod.bluecat.auth.model.bo.LinkUserLocationBO;

import java.util.List;

/**
 * @author jarod.jin 2019/10/10
 */
public interface UserLocationService {

    /**
     * 通过登录名查询组织角色Id
     * @param username 登录名
     * @return List
     */
    List<Long> findOrgRoleIdsByUsername(String username);

    /**
     * 保存用户组织角色信息
     * @param userLocationBO 用户信息
     * @return UserLocationDO
     */
    UserLocationDO saveUserLocation(LinkUserLocationBO userLocationBO);

    /**
     * 删除用户角色权限
     * @param userLocationBO 用户权限
     */
    void delUserLocation(LinkUserLocationBO userLocationBO);
}
