package cn.jarod.bluecat.access.user.service;

import cn.jarod.bluecat.access.group.pojo.entity.GroupPO;
import cn.jarod.bluecat.access.group.pojo.CrudOrganizationBO;
import cn.jarod.bluecat.access.group.service.GroupService;
import cn.jarod.bluecat.access.BlueCatAccessApplicationTest;
import cn.jarod.bluecat.core.api.pojo.TreeDO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/10/23
 */
class OrganizationServiceTest extends BlueCatAccessApplicationTest {

    @Autowired
    private GroupService organizationService;

    private CrudOrganizationBO newOrg;

    private CrudOrganizationBO modifyOrg;

    private CrudOrganizationBO sysOrg;


    @BeforeEach
    void setUp() {
        initNewOrg();
        initModifyOrg ();
        initSysOrg ();
    }

    private void initNewOrg(){
        newOrg = new CrudOrganizationBO();
        newOrg.setNodeId("SYS99999");
        newOrg.setOrgName("系统测试");
        newOrg.setParentId("SYS100001");
        newOrg.setSortOrder(999);
        newOrg.setFullCode("SYS100001/SYS99999");
        newOrg.setFullName("系统管理/系统测试");
        newOrg.setModifier("admin");
        newOrg.setOrgType(0);
    }

    private void initModifyOrg (){
        modifyOrg = new CrudOrganizationBO();
        modifyOrg.setNodeId("SYS100002");
        modifyOrg.setParentId("SYS100001");
        modifyOrg.setOrgName("系统开发部");
        modifyOrg.setSortOrder(1);
        modifyOrg.setFullCode("SYS100001/SYS100002");
        modifyOrg.setFullName("系统管理/系统开发部");
        modifyOrg.setOrgType(0);
        modifyOrg.setModifier("admin");
    }

    private void initSysOrg (){
        sysOrg = new CrudOrganizationBO();
        sysOrg.setNodeId("SYS100001");
        sysOrg.setOrgName("系统管理");
        sysOrg.setSortOrder(1);
        sysOrg.setFullCode("SYS100001");
        sysOrg.setFullName("系统管理");
        sysOrg.setModifier("admin");
        sysOrg.setOrgType(0);
    }

    @AfterEach
    void tearDown() {
        newOrg = null;
        sysOrg = null;
        modifyOrg = null;
    }

    @Test
    @DisplayName("保存新的组织")
    void saveOrganizationNewOne() {
        GroupPO rOrg = organizationService.saveOrganization(newOrg);
        assertNotNull(rOrg.getId());
        newOrg.setId(rOrg.getId());
        organizationService.delOrganization(newOrg);
    }

    @Test
    @DisplayName("修改已有的组织")
    void saveOrganizationModifyOne() {
        modifyOrg.setSortOrder(100);
        GroupPO rOrg = organizationService.saveOrganization(modifyOrg);
        assertEquals(modifyOrg.getSortOrder(),rOrg.getSortOrder());
    }

    @Test
    @DisplayName("根据给定组织代码查询该组织下的组织树")
    void findOrgTreeByOrgCode() {
        List<TreeDO> orgList = organizationService.findOrgTreeByFullCode("SYS100001");
        assertFalse(orgList.isEmpty());
        assertFalse(orgList.get(0).getChildren().isEmpty());
    }
}