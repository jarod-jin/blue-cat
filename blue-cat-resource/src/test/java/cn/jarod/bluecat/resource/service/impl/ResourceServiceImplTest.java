package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.resource.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.resource.entity.DataObjectDO;
import cn.jarod.bluecat.resource.entity.ResourceDO;
import cn.jarod.bluecat.resource.model.bo.CrudResourceBO;
import cn.jarod.bluecat.resource.service.ResourceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
class ResourceServiceImplTest extends BlueCatResourceApplicationTest {

    @Autowired
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        DataObjectDO metadataDO = new DataObjectDO();
        metadataDO.setDescription("测试表2");
        metadataDO.setMemo("随意修改不要删除");
        CrudResourceBO resourceBO = new CrudResourceBO();
        resourceBO.setAccessLevel("public");
        resourceBO.setResourceName("Test Resource 1");
        ResourceDO resourceDO = resourceService.create(resourceBO);
    }

    @Test
    void findAllByAppId() {
    }
}