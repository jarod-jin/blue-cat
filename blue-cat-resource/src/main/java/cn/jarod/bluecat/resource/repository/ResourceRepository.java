package cn.jarod.bluecat.resource.repository;

import cn.jarod.bluecat.resource.entity.ResourceDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public interface ResourceRepository extends MongoRepository<ResourceDO, ObjectId> {
}
