package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.UserInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface UserInfoRepository extends JpaRepository<UserInfoDO,Long> {

    Optional<UserInfoDO> findByUsername(String username);
}
