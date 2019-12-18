package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.UserLocationDO;
import cn.jarod.bluecat.auth.model.bo.LinkUserLocationBO;

import java.util.List;

/**
 * @author jarod.jin 2019/10/10
 */
public interface IUserLocationService {

    /**
     * 通过登录名查询组织角色Id
     * @param username 登录名
     * @return List
     */
    List<Long> findOrgRoleIdsByUsername(String username);

    /**
     * 保存用户组织角色信息
     * @param userLocationBO
     * @return UserLocationDO
     */
    UserLocationDO saveUserLocation(LinkUserLocationBO userLocationBO);

    /**
     * 删除用户角色权限
     * @param userLocationBO 用户权限
     */
    void delUserLocation(LinkUserLocationBO userLocationBO);
}
