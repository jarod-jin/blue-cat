package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.entity.ResourceShareDO;
import cn.jarod.bluecat.auth.model.bo.LinkResourceShareBO;
import cn.jarod.bluecat.auth.model.bo.QueryResourceTreeBO;
import cn.jarod.bluecat.auth.model.bo.CrudResourceBO;
import cn.jarod.bluecat.auth.service.ResourceService;
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
 * @author jarod.jin 2019/11/13
 */
class ResourceServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private ResourceService resourceService;

    private CrudResourceBO tmpResourceBO;

    private LinkResourceShareBO linkRoleResourceBO;

    @BeforeEach
    void setUp() {
        tmpResourceBO = new CrudResourceBO();
        tmpResourceBO.setResourceName("测试菜单");
        tmpResourceBO.setResourceRoute("/tmp");
        tmpResourceBO.setResourceType("m");
        tmpResourceBO.setMemo("基础菜单，测试用");
        tmpResourceBO.setParentCode("");
        tmpResourceBO.setBelongTo("root");
        tmpResourceBO.setModifier("sys");

        linkRoleResourceBO = new LinkResourceShareBO();
    }

    @AfterEach
    void tearDown() {
        tmpResourceBO = null;
    }

    @Test
    @DisplayName("修改资源")
    void saveResource_modify() {
        CrudResourceBO rootResourceBO = new CrudResourceBO();
        rootResourceBO.setResourceCode("RO10001");
        rootResourceBO.setModifier("test");
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
        List<QueryResourceTreeBO> list = resourceService.findResourceTreeBySysAndRoleCodes("root", Lists.newArrayList("admin"));
        assertTrue(list.size()>0);
        assertTrue(list.get(0).isAccess());
    }


    @Test
    @DisplayName("新建资源角色关联")
    void saveResourceShare_new() {
        linkRoleResourceBO.setShareCode("admin");
        linkRoleResourceBO.setShareType(0);
        linkRoleResourceBO.setResourceCode("RO10001");
        ResourceShareDO rDO = resourceService.saveResourceShare(linkRoleResourceBO);
        assertEquals(linkRoleResourceBO.getResourceCode(),rDO.getResourceCode());
    }

}