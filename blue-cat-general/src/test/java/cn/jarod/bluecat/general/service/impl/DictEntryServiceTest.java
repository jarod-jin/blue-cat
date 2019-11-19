package cn.jarod.bluecat.general.service.impl;

import cn.jarod.bluecat.general.BlueCatGeneralApplicationTest;
import cn.jarod.bluecat.general.entity.DictEntryDO;
import cn.jarod.bluecat.general.model.bo.SaveDictEntryBO;
import cn.jarod.bluecat.general.service.IDictEntryService;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @auther jarod.jin 2019/11/19
 */
class DictEntryServiceTest extends BlueCatGeneralApplicationTest {

    @Autowired
    private IDictEntryService dictEntryService;

    private SaveDictEntryBO newDictBO;


    @BeforeEach
    void setUp()  {
        newDictBO = new SaveDictEntryBO();
        newDictBO.setDictCode("test01");
        newDictBO.setEntryJson(ImmutableMap.<String, Object>builder()
                .put("t1","测试1")
                .put("t2","测试2")
                .build());
        newDictBO.setOperator("admin");
    }

    @AfterEach
    void tearDown()  {
        newDictBO = null;
    }

    @Test
    void queryByDictCode() {
    }

    @Test
    void saveDictCode() {
        DictEntryDO dictDO = dictEntryService.saveDictCode(newDictBO);
        assertNotNull(dictDO.getId());
    }
}