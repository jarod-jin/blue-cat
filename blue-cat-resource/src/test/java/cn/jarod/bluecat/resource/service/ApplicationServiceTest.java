package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import org.assertj.core.util.Lists;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/11/20
 */
class ApplicationServiceTest extends BlueCatResourceApplicationTest {

    @Autowired
    private ApplicationService releaseNoteService;

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
        crudReleaseBO.setBuildNo(new BigDecimal("10001.0"));


    }

    @AfterEach
    void tearDown() {
        newApplicationBO = null;
        crudReleaseBO = null;
    }

    @Test
    void queryPage() {
//        QueryReleaseDTO queryDTO = new QueryReleaseDTO();
//        queryDTO.setOrderProperty(new String[]{"buildNo"});
//        queryDTO.setBelongTo("root");
//        queryDTO.setTerminalType("pc");
//        Page<ReleaseDO> page = releaseNoteService.queryPage(queryDTO);
//        assertFalse(page.isEmpty());
    }

    @Test
    void createApplication() {
        ApplicationDO application = releaseNoteService.createApplication(newApplicationBO);
        assertNotNull(application.getId());
    }


    @Test
    void updateApplication() {
        updateApplicationBO.setMemo("用于开发人员单元测试的数据,修改测试2");
        updateApplicationBO.setVersion(4);
        ApplicationDO application = releaseNoteService.updateApplication(updateApplicationBO);
        assertNotNull(application.getId());
    }

    @Test
    void delApplication() {
        newApplicationBO.setId(new ObjectId("5e12e09ed623850344f0c2e1"));
        newApplicationBO.setVersion(0);
        try {
            releaseNoteService.delApplication(newApplicationBO);
        }catch(Exception e){
            fail();
        }
    }


    @Test
    @DisplayName("新建第一个")
    void addReleaseOne() {
        crudReleaseBO.setReleaseVersion("ver 1.0.1");
        crudReleaseBO.setReleaseType("PC");
        crudReleaseBO.setAppId(new ObjectId("5e12ab7e88eee01b6d0f3c5f"));
        crudReleaseBO.setNotes(Lists.newArrayList("完成基础平台搭建","完成数据库和注册中心搭建","初次代码提交"));
        crudReleaseBO.setVersion(9);
        ApplicationDO application = releaseNoteService.addRelease(crudReleaseBO);
        assertNotNull(application.getId());
    }

    @Test
    @DisplayName("增加第二条")
    void addReleaseTwo() {
        crudReleaseBO.setReleaseVersion("ver 1.0.2");
        crudReleaseBO.setNotes(Lists.newArrayList("基础权限平台","第二次代码提交"));
        crudReleaseBO.setVersion(10);
        ApplicationDO application = releaseNoteService.addRelease(crudReleaseBO);
        assertNotNull(application.getId());
    }

}