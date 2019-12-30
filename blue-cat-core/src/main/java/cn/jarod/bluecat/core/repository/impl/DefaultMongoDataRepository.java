package cn.jarod.bluecat.core.repository.impl;


import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.service.MongoDataRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;


/**
 * @author jarod.jin 2019/12/10
 */
@Component
public class DefaultMongoDataRepository<T> implements MongoDataRepository<T> {

    private final MongoTemplate mongoTemplate;

    private final DefaultNoSqlEntityMetadata<T> entityInformation;

    public DefaultMongoDataRepository(DefaultNoSqlEntityMetadata<T> entityInformation, MongoTemplate mongoTemplate) {
        this.entityInformation = entityInformation;
        this.mongoTemplate = mongoTemplate;
    }

    protected Class<T> getEntityClass(){
       return entityInformation.getEntityClass();
    }

    @Override
    @TimeDiff
    public void save(T t){
        mongoTemplate.save(t);
    }

    @Override
    @TimeDiff
    public List<T> queryAllData(){
        return mongoTemplate.findAll(getEntityClass());
    }

    @Override
    @TimeDiff
    public List<T> queryAllDataByQuery(Query query) {
        return mongoTemplate.find(query, getEntityClass());
    }

    @Override
    @TimeDiff
    public Optional<T> queryOneByQuery(Query query) {
        return Optional.ofNullable(mongoTemplate.findOne(query, getEntityClass()));
    }



    @Override
    @TimeDiff
    public Optional<T> queryById(@NotBlank String id){
        return Optional.ofNullable(mongoTemplate.findById(new ObjectId(id), getEntityClass()));
    }


}
