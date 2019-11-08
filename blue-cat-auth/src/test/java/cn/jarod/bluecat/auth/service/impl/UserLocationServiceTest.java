package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.UserLocationDO;
import cn.jarod.bluecat.auth.service.IUserLocationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @auther jarod.jin 2019/11/5
 */
class UserLocationServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IUserLocationService userLocationService;

    private UserLocationDO adminLocationDO;


    @BeforeEach
    void setUp()  {
        adminLocationDO = new UserLocationDO();
        adminLocationDO.setOrgRoleId(1L);
        adminLocationDO.setUsername("admin");
        adminLocationDO.setCreator("admin");
        adminLocationDO.setModifier("admin");
    }

    @AfterEach
    void tearDown()  {
        adminLocationDO = null;
    }

    @Test
    @DisplayName("根据username查询权限")
    void findRoleCodeIdsByUsername(){
        assertTrue(userLocationService.findOrgRoleIdsByUsername("admin").size()>0);
    }

    @Test
    @DisplayName("根据username查询权限")
    void saveUserLocation(){
        assertTrue(userLocationService.saveUserLocation(adminLocationDO));
    }
}