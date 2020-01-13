package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.resource.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.resource.entity.DataObjectDO;
import cn.jarod.bluecat.resource.entity.ResourceDO;
import cn.jarod.bluecat.resource.model.bo.element.CrudHomePageBO;
import cn.jarod.bluecat.resource.service.HomePageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
class HomePageServiceImplTest extends BlueCatResourceApplicationTest {

    @Autowired
    private HomePageService resourceService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        CrudHomePageBO homePageBO = new CrudHomePageBO();


        ResourceDO resourceDO = resourceService.create(homePageBO);
    }

    @Test
    void findAllByAppId() {
    }
}