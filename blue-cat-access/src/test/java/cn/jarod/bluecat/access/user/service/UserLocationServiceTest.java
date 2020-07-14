package cn.jarod.bluecat.access.user.service;


import cn.jarod.bluecat.access.user.entity.UserLocationDO;
import cn.jarod.bluecat.access.user.pojo.LinkUserLocationBO;
import cn.jarod.bluecat.access.BlueCatAccessApplicationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jarod.jin 2019/11/5
 */
class UserLocationServiceTest extends BlueCatAccessApplicationTest {

    @Autowired
    private UserLocationService userLocationService;

    private LinkUserLocationBO adminLocationBO;

    private LinkUserLocationBO tmpLocationBO;


    @BeforeEach
    void setUp()  {
        adminLocationBO = new LinkUserLocationBO();
        adminLocationBO.setOrgRoleId(1L);
        adminLocationBO.setUsername("admin");
        adminLocationBO.setModifier("sys");

        tmpLocationBO = new LinkUserLocationBO();
        tmpLocationBO.setOrgRoleId(0L);
        tmpLocationBO.setUsername("tmp");
        tmpLocationBO.setModifier("sys");
    }

    @AfterEach
    void tearDown()  {
        adminLocationBO = null;
        tmpLocationBO = null;
    }

    @Test
    @DisplayName("根据username查询角色Id列表")
    void findRoleCodeIdsByUsername(){
        assertTrue(userLocationService.findOrgRoleIdsByUsername("admin").size()>0);
    }

    @Test
    @DisplayName("保存角色人员")
    void saveUserLocation(){
        UserLocationDO userLocation = userLocationService.saveUserLocation(tmpLocationBO);
        tmpLocationBO.setId(userLocation.getId());
        userLocationService.delUserLocation(tmpLocationBO);
        assertNotNull(tmpLocationBO.getId());
    }
}