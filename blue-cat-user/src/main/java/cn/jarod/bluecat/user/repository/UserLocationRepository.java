package cn.jarod.bluecat.user.repository;

import cn.jarod.bluecat.user.entity.UserLocationDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jarod.jin 2019/9/9
 */
public interface UserLocationRepository extends JpaRepository<UserLocationDO,Long> {

    /**
     * 根据用户名查询所有对应角色
     * @param username 用户名
     * @return List
     */
    List<UserLocationDO> findAllByUsername(String username);

}
