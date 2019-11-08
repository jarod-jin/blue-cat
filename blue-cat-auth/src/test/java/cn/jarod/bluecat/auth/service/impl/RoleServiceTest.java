package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.SaveRoleBO;
import cn.jarod.bluecat.auth.service.IRoleService;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.BaseQO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/11/5
 */
class RoleServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IRoleService roleService;

    private SaveRoleBO adminDTO;

    private SaveRoleBO tmpDTO;

    @BeforeEach
    void setUp() {
        adminDTO = new SaveRoleBO();
        adminDTO.setRoleName("超级管理员");
        adminDTO.setRoleCode("admin");
        adminDTO.setMemo("本系统的超级管理员，拥有无上权力");
        adminDTO.setDisOrder(1);
        adminDTO.setOperator("sys");

        tmpDTO = new SaveRoleBO();
        tmpDTO.setRoleName("测试用");
        tmpDTO.setRoleCode("test");
        tmpDTO.setMemo("测试用");
        tmpDTO.setDisOrder(1);
        tmpDTO.setOperator("sys");
    }

    @AfterEach
    void tearDown() {
        adminDTO = null;
        tmpDTO = null;
    }

    @Test
    @DisplayName("保存角色")
    void saveRole() {
        assertNotNull(roleService.saveRole(adminDTO).getId());
    }

    @Test
    @DisplayName("删除角色")
    void delRole() {
        roleService.saveRole(tmpDTO);
        try{
            roleService.delRole(tmpDTO);
        }catch (BaseException e){
            fail();
        }
    }

    @Test
    @DisplayName("查询指定Code列表角色的散列表")
    void queryRoleMapByCodes() {
        Map<String, RoleDO> map = roleService.queryRoleMapByCodes(Lists.newArrayList("admin"));
        assertAll("检验返回结果",
                ()-> assertTrue(map.size()>0),
                ()-> assertNotNull(map.get("admin"))
        );
    }


    @Test
    @DisplayName("分页查询所有角色")
    void queryRolePage() {
        Page<RoleDO> page = roleService.queryRolePage(new BaseQO());
        assertAll("检验返回结果",
                ()-> assertFalse(page.isEmpty()),
                ()-> assertEquals("admin",page.getContent().get(0).getRoleCode())
        );
    }


}