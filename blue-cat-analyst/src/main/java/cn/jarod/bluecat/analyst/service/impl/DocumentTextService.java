package cn.jarod.bluecat.analyst.service.impl;

import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import cn.jarod.bluecat.analyst.service.IDocumentTextService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @auther jarod.jin 2019/12/10
 */
@Service
public class DocumentTextService extends MongoDataService<DocumentTextDO> implements IDocumentTextService {

    public DocumentTextService(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

}
