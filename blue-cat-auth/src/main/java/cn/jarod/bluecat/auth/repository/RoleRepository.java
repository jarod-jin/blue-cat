package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface RoleRepository extends JpaRepository<RoleDO,Long> {

    Optional<RoleDO> findByRoleCode(String roleCode);

    List<RoleDO> findAllBySysCodeInAndRoleCodeIn(List<String> sys, List<String> codes);

}

