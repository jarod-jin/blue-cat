package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.UserLocationDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface UserLocationRepository extends JpaRepository<UserLocationDO,Long> {

}
