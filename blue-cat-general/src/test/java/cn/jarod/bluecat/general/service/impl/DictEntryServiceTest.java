package cn.jarod.bluecat.general.service.impl;

import cn.jarod.bluecat.general.BlueCatGeneralApplicationTest;
import cn.jarod.bluecat.general.entity.DictEntryDO;
import cn.jarod.bluecat.general.model.bo.UpdateEntryItemBO;
import cn.jarod.bluecat.general.model.bo.CrudDictEntryBO;
import cn.jarod.bluecat.general.service.IDictEntryService;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/11/19
 */
class DictEntryServiceTest extends BlueCatGeneralApplicationTest {

    @Autowired
    private IDictEntryService dictEntryService;

    private CrudDictEntryBO newDictBO;

    private UpdateEntryItemBO modifyDictBO;

    private UpdateEntryItemBO delDictBO;


    @BeforeEach
    void setUp()  {
        newDictBO = new CrudDictEntryBO();
        newDictBO.setDictCode("test02");
        newDictBO.setMemo("测试字典");
        newDictBO.setEntryJson(ImmutableMap.<String, Object>builder()
                .put("t1","测试1-2")
                .put("t2","测试2-2")
                .put("t3","测试3-2")
                .build());
        newDictBO.setOperator("admin");


        modifyDictBO = new UpdateEntryItemBO();
        modifyDictBO.setDictCode("test01");
        modifyDictBO.setEntryJson(ImmutableMap.<String, Object>builder()
                .put("t4","测试4")
                .put("t3","测试3-1")
                .build());
        modifyDictBO.setOperator("admin");


        delDictBO = new UpdateEntryItemBO();
        delDictBO.setDictCode("test01");
        delDictBO.setEntryJson(ImmutableMap.<String, Object>builder()
                .put("t4","测试4")
                .build());
        delDictBO.setOperator("admin");
    }

    @AfterEach
    void tearDown()  {
        newDictBO = null;
        modifyDictBO = null;
    }

    @Test
    void queryByDictCode() {
        DictEntryDO dictDO = dictEntryService.queryByDictCode("test01");
        assertNotNull(dictDO.getId());
    }

    @Test
    @DisplayName("新建修改字典")
    void saveDict() {
        DictEntryDO dictDO = dictEntryService.saveDict(newDictBO);
        assertNotNull(dictDO.getId());
        assertEquals(newDictBO.getMemo(),dictDO.getMemo());
    }


    @Test
    @DisplayName("修改值")
    void modifyDictEntry() {
        DictEntryDO dictDO = dictEntryService.modifyDictEntry(modifyDictBO);
        assertEquals("测试4",dictDO.getEntryJson().get("t4"));
    }

    @Test
    @DisplayName("删除值")
    void delDictEntry() {
        DictEntryDO dictDO = dictEntryService.delDictEntry(delDictBO);
        assertNull(dictDO.getEntryJson().get("t4"));
    }
}