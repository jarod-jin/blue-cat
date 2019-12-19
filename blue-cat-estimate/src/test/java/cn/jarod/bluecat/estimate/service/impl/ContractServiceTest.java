package cn.jarod.bluecat.estimate.service.impl;

import cn.jarod.bluecat.estimate.BlueCatEstimateApplicationTest;
import cn.jarod.bluecat.estimate.entity.ConditionDO;
import cn.jarod.bluecat.estimate.entity.ContractItemDO;
import cn.jarod.bluecat.estimate.entity.ContractSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;
import cn.jarod.bluecat.estimate.service.ContractService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/11/26
 */
class ContractServiceTest extends BlueCatEstimateApplicationTest {

    @Autowired
    private ContractService contractService;

    private CrudContractSheetBO newSheetBO;

    private List<CrudContractItemBO> newItemList;

    @BeforeEach
    void setUp() {
        newSheetBO = new CrudContractSheetBO();
        newSheetBO.setSerialNo("SC20191126");
        newSheetBO.setCustomerNo("customer2019");
        newSheetBO.setSysCode("root");
        newSheetBO.setContractName("测试智能合约");
        newSheetBO.setContractText("测试智能合约说明文档v1.0");
        newSheetBO.setModifier("admin");


        newItemList = Lists.newArrayList();
        //插入itemA
        CrudContractItemBO itemA = new CrudContractItemBO();
        itemA.setId(5L);
        itemA.setConditionMark(1);
        itemA.setSerialNo("SC20191126");
        itemA.setItemScore(new BigDecimal(10));
        itemA.setItemNo(1);
        itemA.setItemText("这是第一题");
        itemA.setModifier("admin");
        itemA.setSysCode("root");
        List<ConditionDO> dos = Lists.newArrayList();
        ConditionDO conditionA = new ConditionDO("A","选项A",new BigDecimal(0),new BigDecimal(0),"");
        dos.add(conditionA);
        ConditionDO conditionB = new ConditionDO("B","选项B",new BigDecimal(0),new BigDecimal(10),"a=='B'");
        dos.add(conditionB);
        ConditionDO conditionC = new ConditionDO("C","选项C",new BigDecimal(0),new BigDecimal(0),"");
        dos.add(conditionC);
        itemA.setConditionJson(dos);
        newItemList.add(itemA);

        //itemB插入
        CrudContractItemBO itemB = new CrudContractItemBO();
        itemB.setId(6L);
        itemB.setConditionMark(1);
        itemB.setSerialNo("SC20191126");
        itemB.setItemScore(new BigDecimal(10));
        itemB.setItemNo(2);
        itemB.setItemText("这是第二题");
        itemB.setModifier("admin");
        itemB.setSysCode("root");
        List<ConditionDO> dosB = Lists.newArrayList();
        ConditionDO condA = new ConditionDO("A","选项A",new BigDecimal(0),new BigDecimal(0),"");
        dosB.add(condA);
        ConditionDO condB = new ConditionDO("B","选项B",new BigDecimal(0),new BigDecimal(0),"");
        dosB.add(condB);
        ConditionDO condC = new ConditionDO("C","选项C",new BigDecimal(0),new BigDecimal(10),"a=='C'");
        dosB.add(condC);
        itemB.setConditionJson(dosB);
        newItemList.add(itemB);
    }

    @AfterEach
    void tearDown() {
        newSheetBO = null;
        newItemList = null;
    }

    @Test
    @DisplayName("新建Sheet")
    void saveSheet() {
        ContractSheetDO sheetDO = contractService.saveContractSheet(newSheetBO);
        assertNotNull(sheetDO.getId());
    }

    @Test
    void saveItemList() {
        List<ContractItemDO> itemList = contractService.saveContractItemList(newItemList);
        assertTrue(itemList.size()>0);

    }

    @Test
    void findContract() {
        CrudContractSheetBO sheetBO = new CrudContractSheetBO("SC20191126","root");
        CrudContractSheetBO contractSheetBO = contractService.findContract(sheetBO);
        assertFalse(contractSheetBO.getContractItemBOList().isEmpty());
    }
}