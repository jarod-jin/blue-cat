package cn.jarod.bluecat.analyst.service.impl;

import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import cn.jarod.bluecat.analyst.service.IDocumentTextService;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther jarod.jin 2019/12/5
 */
@Service
public class DocumentTextService implements IDocumentTextService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DocumentTextService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @TimeDiff
    public void save(DocumentTextDO entity, String collectionName) {
        mongoTemplate.save(entity, collectionName);
    }

    @Override
    @TimeDiff
    public List<DocumentTextDO> getAllData(String collectionName) {
        return mongoTemplate.findAll(DocumentTextDO.class, collectionName);
    }
}
