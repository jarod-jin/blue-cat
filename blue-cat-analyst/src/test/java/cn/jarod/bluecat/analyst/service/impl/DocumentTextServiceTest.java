package cn.jarod.bluecat.analyst.service.impl;

import cn.jarod.bluecat.analyst.BlueCatAnalystApplicationTest;
import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import cn.jarod.bluecat.analyst.service.IDocumentTextService;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther jarod.jin 2019/12/5
 */
class DocumentTextServiceTest extends BlueCatAnalystApplicationTest {

    @Autowired
    private IDocumentTextService documentTextService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        DocumentTextDO entity = new DocumentTextDO(ObjectId.get());
        entity.setSubject("测试");
        entity.setContextList(Lists.newArrayList("开源版与专业版的区别？",
                "AMS开源版 专为微型团队设计，拥有基础的API管理、测试以及团队协作功能；",
                "AMS专业版 则专注于企业级用户，除了在API管理、基础测试、自动化测试等方面有所强化外，在人员管理方面支持企业级人员架构，允许对用户进行权限分组以及设置详细的操作权限等。",
                "如果您是专业用户，AMS专业版将更能满足您的需求，点击这里了解更多！"
        ));
        documentTextService.save(entity, "resume");
    }

    @Test
    void getAllData() {
    }
}