package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.RoleUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface OrgAuthRepository extends JpaRepository<RoleUserDO,Long> {

}
