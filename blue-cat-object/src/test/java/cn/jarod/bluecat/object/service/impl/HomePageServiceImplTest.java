package cn.jarod.bluecat.object.service.impl;

import cn.jarod.bluecat.object.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.object.entity.HomePageDO;
import cn.jarod.bluecat.object.model.bo.element.CrudHomePageBO;
import cn.jarod.bluecat.object.model.dto.HomePageQuery;
import cn.jarod.bluecat.object.service.HomePageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

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
        homePageBO.setTerminalType("pc");
        homePageBO.setText("测试系统");
        homePageBO.setRoutePath("/blue-cat/");
        HomePageDO resourceDO = homePageService.create(homePageBO);
        assertNotNull(resourceDO.getId());
    }

    @Test
    void findAllByPage()  {
        HomePageQuery query = new HomePageQuery();
        query.setBelongTo("5e12ab7e88eee01b6d0f3c5f");
        query.setTerminalType("pc");
        Page<HomePageDO> page = homePageService.findAllByPage(query);
        assertFalse(page.isEmpty());
        assertTrue(page.get().allMatch(pageDO->pageDO.getText().contains("测试")));
    }
}