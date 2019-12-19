package cn.jarod.bluecat.analyst.service.impl;


import cn.jarod.bluecat.analyst.service.MongoDataService;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author jarod.jin 2019/12/10
 */
@Service
public class MongoDataServiceImpl<T> implements MongoDataService<T> {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDataServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @TimeDiff
    public void save(T t, String collectionName){
        mongoTemplate.save(t, collectionName);
    }

    @Override
    @TimeDiff
    public List<T> queryAllData(String collectionName, Class<T> clazz){
        return mongoTemplate.findAll(clazz, collectionName);
    }

    @Override
    @TimeDiff
    public List<T> queryAllDataByQuery(Query query, String collectionName, Class<T> clazz) {
        return mongoTemplate.find(query, clazz, collectionName);
    }

    @Override
    @TimeDiff
    public <T> T queryOneByQuery(Query query, String collectionName, Class<T> clazz) {
        return mongoTemplate.findOne(query, clazz, collectionName);
    }



    @Override
    @TimeDiff
    public <T> T queryById(String id, String collectionName, Class<T> clazz){
        return mongoTemplate.findById(new ObjectId(id),clazz, collectionName);
    }
}
