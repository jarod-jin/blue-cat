package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.model.dto.RoleDTO;
import cn.jarod.bluecat.auth.service.IRoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther jarod.jin 2019/11/5
 */
class RoleServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IRoleService roleService;

    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTO();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveRole() {
    }
}