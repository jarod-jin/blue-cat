package cn.jarod.bluecat.analyst.service;

import java.util.List;

/**
 * @auther jarod.jin 2019/12/5
 */
public interface IMongoDataService<T> {

    void save(T t, String collectionName);

    List<T> getAllData(String collectionName,Class<T> clazz);

}
