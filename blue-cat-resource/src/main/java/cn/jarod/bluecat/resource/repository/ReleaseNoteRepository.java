package cn.jarod.bluecat.resource.repository;

import cn.jarod.bluecat.resource.entity.ReleaseNoteDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/30
 */
public interface ReleaseNoteRepository extends MongoRepository<ReleaseNoteDO, ObjectId> {
}
