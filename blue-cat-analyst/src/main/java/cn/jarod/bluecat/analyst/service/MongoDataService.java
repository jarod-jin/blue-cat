package cn.jarod.bluecat.analyst.service;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


/**
 * @author jarod.jin 2019/12/5
 */
public interface MongoDataService<T> {

    void save(T t, String collectionName);

    List<T> queryAllData(String collectionName, Class<T> clazz);

    List<T> queryAllDataByQuery(Query query, String collectionName, Class<T> clazz);

    <T> T queryOneByQuery(Query query, String collectionName, Class<T> clazz);

    <T> T queryById(String id, String collectionName, Class<T> clazz);

}
