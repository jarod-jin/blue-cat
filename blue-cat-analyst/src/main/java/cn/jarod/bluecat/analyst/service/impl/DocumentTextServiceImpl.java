package cn.jarod.bluecat.analyst.service.impl;

import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import cn.jarod.bluecat.analyst.service.DocumentTextService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author jarod.jin 2019/12/10
 */
@Service
public class DocumentTextServiceImpl extends MongoDataServiceImpl<DocumentTextDO> implements DocumentTextService {

    public DocumentTextServiceImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

}
