package cn.jarod.bluecat.core.repository;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;


/**
 * @author jarod.jin 2019/12/5
 */
public interface MongoDataRepository<T>{

    /**
     * 保存数据
     * @param t 保存对象
     */
    void save(T t);

    /**
     * 查询整个集合
     * @return 返回集合
     */
    List<T>  queryAllData();

    /**
     * 根据条件查询结果集
     * @param query 查询条件
     * @return List
     */
    List<T> queryAllDataByQuery(Query query);

    /**
     * 查询一个
     * @param query 查询条件
     * @return 单个对象
     */
    Optional<T> queryOneByQuery(Query query);

    /**
     * 根据Id查询
     * @param id ObjectId
     * @return 单个数据
     */
    Optional<T> queryById(String id);

}
