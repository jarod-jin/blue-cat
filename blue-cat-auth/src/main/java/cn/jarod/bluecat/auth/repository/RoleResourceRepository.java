package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface RoleResourceRepository extends JpaRepository<RoleResourceDO,Long> {

    Optional<RoleResourceDO> findByResourceCodeAndRoleCode(String resourceCode);
}

