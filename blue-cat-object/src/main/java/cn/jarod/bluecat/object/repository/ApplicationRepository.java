package cn.jarod.bluecat.object.repository;

import cn.jarod.bluecat.object.entity.ApplicationDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/30
 */
public interface ApplicationRepository extends MongoRepository<ApplicationDO, ObjectId> {
}
