package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.UserLocationDO;

import java.util.List;

/**
 * @auther jarod.jin 2019/10/10
 */
public interface IUserLocationService {

    List<Long> findOrgRoleIdsByUsername(String name);

    UserLocationDO saveUserLocation(UserLocationDO adminLocationDO);
}
