package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.resource.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.resource.entity.HomePageDO;
import cn.jarod.bluecat.resource.model.bo.element.CrudHomePageBO;
import cn.jarod.bluecat.resource.service.HomePageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
class HomePageServiceImplTest extends BlueCatResourceApplicationTest {

    @Autowired
    private HomePageService homePageService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("创建主页")
    void createHomePage() {
        CrudHomePageBO homePageBO = new CrudHomePageBO();
        homePageBO.setBelongTo("5e12ab7e88eee01b6d0f3c5f");
        homePageBO.setModifier("admin");
        homePageBO.setTerminalVersion("pc 1.0 release");
        homePageBO.setText("测试系统");
        homePageBO.setRoutePath("/blue-cat/");
        HomePageDO resourceDO = homePageService.create(homePageBO);
        assertNotNull(resourceDO.getId());
    }

    @Test
    void findAllByAppId() {
    }
}