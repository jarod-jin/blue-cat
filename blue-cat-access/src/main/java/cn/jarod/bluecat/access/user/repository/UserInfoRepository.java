package cn.jarod.bluecat.access.user.repository;

import cn.jarod.bluecat.access.user.entity.UserInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface UserInfoRepository extends JpaRepository<UserInfoDO,Long> {

    /**
     * 根据用户名查询用户基本信息
     * @param username  用户名
     * @return Optional
     */
    Optional<UserInfoDO> findByUsername(String username);
}
