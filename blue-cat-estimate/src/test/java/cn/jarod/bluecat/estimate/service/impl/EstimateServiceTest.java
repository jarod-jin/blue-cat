package cn.jarod.bluecat.estimate.service.impl;

import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.estimate.BlueCatEstimateApplicationTest;
import cn.jarod.bluecat.estimate.entity.AnswerDO;
import cn.jarod.bluecat.estimate.entity.EstimateItemDO;
import cn.jarod.bluecat.estimate.entity.EstimateSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;
import cn.jarod.bluecat.estimate.service.IEstimateService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/11/27
 */
@Slf4j
class EstimateServiceTest extends BlueCatEstimateApplicationTest {


    @Autowired
    private IEstimateService estimateService;

    private CrudEstimateSheetBO newSheetBO;

    private List<CrudEstimateItemBO> newItemList;


    @BeforeEach
    void setUp() {
        newSheetBO = new CrudEstimateSheetBO();
        newSheetBO.setSerialNo("SC20191126");
        newSheetBO.setFinishedMark(1);
        newSheetBO.setSysCode("root");
        newSheetBO.setUsername("admin");
        newSheetBO.setNickname("管理员");
        newSheetBO.setModifier("admin");

        newItemList = Lists.newArrayList();
        //插入itemA
        CrudEstimateItemBO itemA = new CrudEstimateItemBO();
        itemA.setId(3L);
        itemA.setSerialNo("SC20191126");
        itemA.setItemNo(1);
        itemA.setEstimateSheetId(1L);
        itemA.setModifier("admin");
        itemA.setSysCode("root");
        List<AnswerDO> dos = Lists.newArrayList();
        AnswerDO answerA = new AnswerDO("A","");
        dos.add(answerA);
        AnswerDO answerB = new AnswerDO("B","");
        dos.add(answerB);
        AnswerDO answerC = new AnswerDO("C","C");
        dos.add(answerC);
        itemA.setAnswerJson(dos);
        newItemList.add(itemA);

        //itemB插入
        CrudEstimateItemBO itemB = new CrudEstimateItemBO();
        itemB.setId(4L);
        itemB.setSerialNo("SC20191126");
        itemB.setItemNo(2);
        itemB.setEstimateSheetId(1L);
        itemB.setModifier("admin");
        itemB.setSysCode("root");
        List<AnswerDO> dosB = Lists.newArrayList();
        AnswerDO condA = new AnswerDO("A","");
        dosB.add(condA);
        AnswerDO condB = new AnswerDO("B","B");
        dosB.add(condB);
        AnswerDO condC = new AnswerDO("C","");
        dosB.add(condC);
        itemB.setAnswerJson(dosB);
        newItemList.add(itemB);

    }

    @AfterEach
    void tearDown() {
        newSheetBO = null;
    }

    @Test
    void saveSheet() {
        EstimateSheetDO sheetDO = estimateService.saveEstimateSheet(newSheetBO);
        assertNotNull(sheetDO.getId());
    }

    @Test
    void saveItemList() {
        List<EstimateItemDO> list = estimateService.saveEstimateItemList(newItemList);
        assertTrue(list.size()>0);
    }

    @Test
    void findContract() {
        CrudEstimateSheetBO sheetBO = new CrudEstimateSheetBO("SC20191126","admin","root", Const.NOT_DEL);
        CrudEstimateSheetBO resultSheetBO = estimateService.findEstimate(sheetBO);
        assertFalse(resultSheetBO.getCrudEstimateItemList().isEmpty());
    }

    @Test
    void testDemo() throws ScriptException {
        String str = "(a >= 0 && a <= 5)";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("a", "4");
        Object result = engine.eval(str);
        System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
    }
}