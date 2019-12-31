package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.BlueCatResourceApplicationTest;
import cn.jarod.bluecat.resource.entity.DictDO;
import cn.jarod.bluecat.resource.model.bo.UpdateDictItemBO;
import cn.jarod.bluecat.resource.model.bo.CrudDictBO;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/11/19
 */
class DictEntryServiceTest extends BlueCatResourceApplicationTest {

    @Autowired
    private DictService dictEntryService;

    private CrudDictBO newDictBO;

    private UpdateDictItemBO modifyDictBO;

    private UpdateDictItemBO delDictBO;


    @BeforeEach
    void setUp()  {
        newDictBO = new CrudDictBO();
        newDictBO.setCategory("test03");
        newDictBO.setMemo("测试字典");
        newDictBO.setItems(new ImmutableSortedMap.Builder<String, Object>(Ordering.natural())
                .put("t1","测试1-2")
                .put("t2","测试2-2")
                .put("t3","测试3-2")
                .build());
        newDictBO.setModifier("admin");


        modifyDictBO = new UpdateDictItemBO();
        modifyDictBO.setCategory("test01");
        modifyDictBO.setItems(new ImmutableSortedMap.Builder<String, Object>(Ordering.natural())
                .put("t4","测试4")
                .put("t3","测试3-1")
                .build());
        modifyDictBO.setModifier("admin");


        delDictBO = new UpdateDictItemBO();
        delDictBO.setCategory("test01");
        delDictBO.setItems(new ImmutableSortedMap.Builder<String, Object>(Ordering.natural())
                .put("t4","测试4")
                .build());
        delDictBO.setModifier("admin");
    }

    @AfterEach
    void tearDown()  {
        newDictBO = null;
        modifyDictBO = null;
    }

    @Test
    @DisplayName("根据类别查询字典")
    void findByCategory() {
        DictDO dictDO = dictEntryService.findByCategory("test02");
        assertNotNull(dictDO.getId());
    }

    @Test
    @DisplayName("新建字典")
    void createDict() {
        DictDO dictDO = dictEntryService.create(newDictBO);
        assertNotNull(dictDO.getId());
        assertEquals(newDictBO.getMemo(),dictDO.getMemo());
    }


    @Test
    @DisplayName("修改字典")
    void updateDict() {
        DictDO dictDO = dictEntryService.update(newDictBO);
        assertNotNull(dictDO.getId());
        assertEquals(newDictBO.getMemo(),dictDO.getMemo());
    }


    @Test
    @DisplayName("修改值")
    void modifyDictEntry() {
        DictDO dictDO = dictEntryService.updateItems(modifyDictBO);
        assertEquals("测试4",dictDO.getItems().get("t4"));
    }

    @Test
    @DisplayName("删除值")
    void delDictEntry() {
        DictDO dictDO = dictEntryService.delItem(delDictBO);
        assertNull(dictDO.getItems().get("t4"));
    }
}