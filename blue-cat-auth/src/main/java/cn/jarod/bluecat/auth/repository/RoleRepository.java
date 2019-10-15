package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface RoleRepository extends JpaRepository<RoleDO,Long> {

}
