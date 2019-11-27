package cn.jarod.bluecat.general.service.impl;

import cn.jarod.bluecat.general.BlueCatGeneralApplicationTest;
import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import cn.jarod.bluecat.general.model.bo.CrudReleaseNoteBO;
import cn.jarod.bluecat.general.model.dto.QueryReleaseDTO;
import cn.jarod.bluecat.general.service.IReleaseNoteService;
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
 * @auther jarod.jin 2019/11/20
 */
class ReleaseNoteServiceTest extends BlueCatGeneralApplicationTest {

    @Autowired
    private IReleaseNoteService releaseNoteService;

    private CrudReleaseNoteBO crudReleaseNoteBO;

    @BeforeEach
    void setUp() {
        crudReleaseNoteBO = new CrudReleaseNoteBO();
        crudReleaseNoteBO.setReleaseVersion("ver 1.0.1");
        crudReleaseNoteBO.setSysCode("root");
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
        queryDTO.setSysCode("root");
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