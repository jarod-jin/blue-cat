package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.model.dto.RoleDTO;
import cn.jarod.bluecat.auth.service.IRoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        roleDTO.setRoleName("超级管理员");
        roleDTO.setRoleCode("admin");
        roleDTO.setMemo("本系统的超级管理员，拥有无上权力");
        roleDTO.setDisOrder(1);
        roleDTO.setOperator("admin");
    }

    @AfterEach
    void tearDown() {
        roleDTO = null;
    }

    @Test
    void saveRole() {
        assertNotNull(roleService.saveRole(roleDTO).getId());
    }
}