package cn.jarod.bluecat.object.repository;

import cn.jarod.bluecat.object.entity.HomePageDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public interface HomePageRepository extends MongoRepository<HomePageDO, ObjectId> {
}
