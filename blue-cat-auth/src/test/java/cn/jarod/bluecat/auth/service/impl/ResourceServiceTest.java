package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import cn.jarod.bluecat.auth.model.bo.LinkRoleResourceBO;
import cn.jarod.bluecat.auth.model.bo.QueryResourceTreeBO;
import cn.jarod.bluecat.auth.model.bo.SaveResourceBO;
import cn.jarod.bluecat.auth.service.IResourceService;
import cn.jarod.bluecat.core.exception.BaseException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/11/13
 */
class ResourceServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IResourceService resourceService;

    private SaveResourceBO tmpResourceBO;

    private LinkRoleResourceBO linkRoleResourceBO;

    @BeforeEach
    void setUp() {
        tmpResourceBO = new SaveResourceBO();
        tmpResourceBO.setResourceName("测试菜单");
        tmpResourceBO.setResourceRoute("/tmp");
        tmpResourceBO.setResourceType("m");
        tmpResourceBO.setMemo("基础菜单，测试用");
        tmpResourceBO.setParentCode("");
        tmpResourceBO.setSysCode("root");
        tmpResourceBO.setOperator("sys");

        linkRoleResourceBO = new LinkRoleResourceBO();
    }

    @AfterEach
    void tearDown() {
        tmpResourceBO = null;
    }

    @Test
    @DisplayName("修改资源")
    void saveResource_modify() {
        SaveResourceBO rootResourceBO = new SaveResourceBO();
        rootResourceBO.setResourceCode("RO10001");
        rootResourceBO.setOperator("test");
        rootResourceBO.setMemo("基础菜单，请勿删除");
        ResourceDO rDO = resourceService.saveResource(rootResourceBO);
        assertEquals(rootResourceBO.getMemo(),rDO.getMemo());
        assertEquals("/root",rDO.getResourceRoute());
    }

    @Test
    @DisplayName("新建资源")
    void saveResource_new() {
        ResourceDO rDO = resourceService.saveResource(tmpResourceBO);
        assertEquals(tmpResourceBO.getMemo(),rDO.getMemo());
    }


    @Test
    @DisplayName("删除资源")
    void delResource() {
        tmpResourceBO.setResourceCode("RO10002");
        try {
            resourceService.delResource(tmpResourceBO);
        }catch (BaseException e){
            fail();
        }

    }

    @Test
    void queryResourceListByCodes() {
        List<QueryResourceTreeBO> list = resourceService.queryResourceTreeBySysAndRoleCodes("root", Lists.newArrayList("admin"));
        assertTrue(list.size()>0);
        assertTrue(list.get(0).isAccess());
    }


    @Test
    @DisplayName("新建资源角色关联")
    void saveRoleResource_new() {
        linkRoleResourceBO.setRoleCode("admin");
        linkRoleResourceBO.setResourceCode("RO10001");
        RoleResourceDO rDO = resourceService.saveRoleResource(linkRoleResourceBO);
        assertEquals(linkRoleResourceBO.getResourceCode(),rDO.getResourceCode());
    }

}