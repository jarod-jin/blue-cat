package cn.jarod.bluecat.object.repository;

import cn.jarod.bluecat.object.entity.DictDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/30
 */
public interface DictRepository extends MongoRepository<DictDO, ObjectId> {
    /**
     * 根据类别查询
     * @param category 类别名
     * @param belongTo 所属系统
     * @return  Optional<DictDO>
     */
    Optional<DictDO> findOneByCategoryAndBelongTo(String category, String belongTo);
}
