package cn.jarod.bluecat.analyst.procedure;

import cn.jarod.bluecat.analyst.BlueCatAnalystApplicationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jarod.jin 2019/12/10
 */
class DocumentAnalyzeTest extends BlueCatAnalystApplicationTest {

    @Autowired
    DocumentAnalyze documentAnalyze;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCandidateBySubject() {
        documentAnalyze.createCandidateBySubject("杨东炎-前端.docx");
    }
}