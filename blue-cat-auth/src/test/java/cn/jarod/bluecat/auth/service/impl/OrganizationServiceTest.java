package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.auth.service.IOrganizationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/10/23
 */
class OrganizationServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IOrganizationService organizationService;

    private OrganizationDTO newOrg;

    private OrganizationDTO sysOrg;


    @BeforeEach
    void setUp() {
        newOrg = new OrganizationDTO();
        newOrg.setNode("SYS99999");
        newOrg.setOrgName("系统测试部");
        newOrg.setPNode("SYS100001");
        newOrg.setDisOrder(999);
        newOrg.setFullCode("SYS100001/SYS99999");
        newOrg.setFullName("系统管理/系统测试部");
        newOrg.setOrgType(0);

        sysOrg = new OrganizationDTO();
        sysOrg.setNode("SYS100001");
        sysOrg.setOrgName("系统管理");
        sysOrg.setDisOrder(1);
        sysOrg.setFullCode("SYS100001");
        sysOrg.setFullName("系统管理");
        sysOrg.setOrgType(0);

    }

    @AfterEach
    void tearDown() {
        newOrg = null;
        sysOrg = null;
    }

    @Test
    @DisplayName("保存新的组织")
    void saveOrganizationNewOne() {
        OrganizationDTO rOrg = organizationService.saveOrganization(newOrg);
        assertNotNull(rOrg.getId());
        organizationService.delOrganization(rOrg);
    }
}