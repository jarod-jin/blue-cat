package cn.jarod.bluecat.estimate.procedure;

import cn.jarod.bluecat.estimate.BlueCatEstimateApplicationTest;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/11/29
 */
class ScoreEstimateTest extends BlueCatEstimateApplicationTest {

    @Autowired
    private ScoreEstimate scoreEstimate;

    private CrudEstimateSheetBO newSheetBO;


    @BeforeEach
    void setUp() {
        newSheetBO = new CrudEstimateSheetBO();
        newSheetBO.setSerialNo("SC20191126");
        newSheetBO.setSysCode("root");
        newSheetBO.setUsername("admin");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void countScoreByEstimateSheet() {
        assertDoesNotThrow(()->scoreEstimate.countScoreByEstimateSheet(newSheetBO));
    }
}