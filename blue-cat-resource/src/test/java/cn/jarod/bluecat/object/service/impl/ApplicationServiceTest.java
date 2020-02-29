package cn.jarod.bluecat.object.service.impl;

import cn.jarod.bluecat.object.BlueCatObjectApplicationTest;
import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.entity.ReleaseDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import cn.jarod.bluecat.resource.model.dto.ApplicationQuery;
import cn.jarod.bluecat.resource.service.ApplicationService;
import org.assertj.core.util.Lists;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/11/20
 */
class ApplicationServiceTest extends BlueCatObjectApplicationTest {

    @Autowired
    private ApplicationService applicationService;

    private CrudReleaseBO crudReleaseBO;

    private CrudApplicationBO newApplicationBO;

    private CrudApplicationBO updateApplicationBO;

    @BeforeEach
    void setUp() {
        newApplicationBO = new CrudApplicationBO();
        newApplicationBO.setModifier("admin");
        newApplicationBO.setDescription("测试系统2");
        newApplicationBO.setMemo("用于开发人员单元测试的数据");

        updateApplicationBO = new CrudApplicationBO();
        updateApplicationBO.setModifier("admin");
        updateApplicationBO.setId(new ObjectId("5e12ab7e88eee01b6d0f3c5f"));

        crudReleaseBO = new CrudReleaseBO();
        crudReleaseBO.setReleaseVersion("ver 1.0.1");
        crudReleaseBO.setReleaseType("PC");
        crudReleaseBO.setAppId(new ObjectId("5e12ab7e88eee01b6d0f3c5f"));
        crudReleaseBO.setNotes(Lists.newArrayList("完成基础平台搭建","完成数据库和注册中心搭建","初次代码提交"));


    }

    @AfterEach
    void tearDown() {
        newApplicationBO = null;
        crudReleaseBO = null;
    }

    @Test
    void queryApplicationPage() {
        ApplicationQuery queryDTO = new ApplicationQuery();
        queryDTO.setDescription("测试");
        Page<ApplicationDO> page = applicationService.findAllApplication(queryDTO);
        assertFalse(page.isEmpty());
        assertTrue(page.get().allMatch(app->app.getDescription().contains("测试")));
    }


    @Test
    void createApplication() {
        ApplicationDO application = applicationService.createApplication(newApplicationBO);
        assertNotNull(application.getId());
    }


    @Test
    void updateApplication() {
        updateApplicationBO.setMemo("用于开发人员单元测试的数据,修改测试2");
        updateApplicationBO.setVersion(4);
        ApplicationDO application = applicationService.updateApplication(updateApplicationBO);
        assertNotNull(application.getId());
    }

    @Test
    void delApplication() {
        newApplicationBO.setId(new ObjectId("5e12e09ed623850344f0c2e1"));
        newApplicationBO.setVersion(0);
        try {
            applicationService.delApplication(newApplicationBO);
        }catch(Exception e){
            fail();
        }
    }


    @Test
    @DisplayName("新建第一个")
    void addReleaseOne() {
        crudReleaseBO.setReleaseVersion("ver 1.0.1");
        crudReleaseBO.setReleaseType("PC");
        crudReleaseBO.setBuildNo(new BigDecimal("10001.0"));
        crudReleaseBO.setAppId(new ObjectId("5e12ab7e88eee01b6d0f3c5f"));
        crudReleaseBO.setNotes(Lists.newArrayList("完成基础平台搭建","完成数据库和注册中心搭建","初次代码提交"));
        crudReleaseBO.setVersion(9);
        ApplicationDO application = applicationService.addRelease(crudReleaseBO);
        assertNotNull(application.getId());
    }

    @Test
    @DisplayName("增加第二条")
    void addReleaseTwo() {
        crudReleaseBO.setReleaseVersion("ver 1.0.2");
        crudReleaseBO.setBuildNo(new BigDecimal("10002.0"));
        crudReleaseBO.setNotes(Lists.newArrayList("基础权限平台","第二次代码提交"));
        crudReleaseBO.setVersion(10);
        ApplicationDO application = applicationService.addRelease(crudReleaseBO);
        assertNotNull(application.getId());
    }

    @Test
    @DisplayName("查询最新的")
    void findLatestRelease() {
        ReleaseDO releaseDO = applicationService.findLatestRelease(new ObjectId("5e12ab7e88eee01b6d0f3c5f"),"PC");
        assertNotNull(releaseDO);
        assertEquals(new BigDecimal("10002.0"),releaseDO.getBuildNo());
    }

}