package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.model.dto.OrgRoleDTO;
import cn.jarod.bluecat.auth.service.IOrgRoleService;
import cn.jarod.bluecat.core.exception.BaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/11/5
 */
class OrgRoleServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IOrgRoleService orgRoleService;

    private OrgRoleDTO sysAdminDTO;

    private OrgRoleDTO tmpDTO;


    @BeforeEach
    void setUp() {
        sysAdminDTO = new OrgRoleDTO();
        sysAdminDTO.setRoleCode("admin");
        sysAdminDTO.setOrgCode("SYS100001");

        tmpDTO = new OrgRoleDTO();
        tmpDTO.setRoleCode("test");
        tmpDTO.setOrgCode("SYS99999");
    }

    @AfterEach
    void tearDown() {
        sysAdminDTO = null;
    }

    @Test
    @DisplayName("保存组织下角色")
    void saveOrgRole() {
        OrgRoleDO orgRoleDO = orgRoleService.saveOrgRole(tmpDTO);
        assertAll("检验返回结果",
                ()-> assertNotNull(orgRoleDO.getId()),
                ()-> assertEquals("test",orgRoleDO.getRoleCode()),
                ()-> assertEquals("SYS99999",orgRoleDO.getOrgCode())
        );
        try{
            orgRoleService.delOrgRole(tmpDTO);
        }catch (BaseException e){
            fail();
        }
    }

}