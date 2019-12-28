package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.resource.entity.ReleaseNoteDO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseNoteBO;
import cn.jarod.bluecat.resource.model.dto.QueryReleaseDTO;
import cn.jarod.bluecat.resource.service.ReleaseNoteService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author jarod.jin 2019/11/20
 */
class ReleaseNoteServiceTest extends BlueCatResourceApplicationTest {

    @Autowired
    private ReleaseNoteService releaseNoteService;

    private CrudReleaseNoteBO crudReleaseNoteBO;

    @BeforeEach
    void setUp() {
        crudReleaseNoteBO = new CrudReleaseNoteBO();
        crudReleaseNoteBO.setReleaseVersion("ver 1.0.1");
        crudReleaseNoteBO.setBelongTo("root");
        crudReleaseNoteBO.setTerminalType("pc");
        crudReleaseNoteBO.setReleaseNote(Lists.newArrayList("完成基础平台搭建","完成数据库和注册中心搭建"));
        crudReleaseNoteBO.setModifier("admin");
        crudReleaseNoteBO.setBuildNo(new BigDecimal(10001.0));
    }

    @AfterEach
    void tearDown() {
        crudReleaseNoteBO = null;
    }

    @Test
    void queryPage() {
        QueryReleaseDTO queryDTO = new QueryReleaseDTO();
        queryDTO.setOrderProperty(new String[]{"buildNo"});
        queryDTO.setBelongTo("root");
        queryDTO.setTerminalType("pc");
        Page<ReleaseNoteDO> page = releaseNoteService.queryPage(queryDTO);
        assertFalse(page.isEmpty());
    }

    @Test
    void saveReleaseNote() {
        ReleaseNoteDO releaseDO = releaseNoteService.saveReleaseNote(crudReleaseNoteBO);
        assertNotNull(releaseDO.getId());
    }
}