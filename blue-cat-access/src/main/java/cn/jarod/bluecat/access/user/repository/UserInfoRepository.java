package cn.jarod.bluecat.access.user.repository;

import cn.jarod.bluecat.access.user.pojo.entity.UserInfoPO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface UserInfoRepository extends MongoRepository<UserInfoPO, ObjectId> {

    /**
     * 根据用户名查询用户基本信息
     * @param username  用户名
     * @return Optional
     */
    Optional<UserInfoPO> findByUsername(String username);
}
