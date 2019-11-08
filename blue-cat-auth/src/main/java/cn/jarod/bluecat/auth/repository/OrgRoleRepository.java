package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface OrgRoleRepository extends JpaRepository<OrgRoleDO,Long> {

    List<OrgRoleDO> findAllByRoleCodeIn(List<String> codeList);

    Optional<OrgRoleDO> findByOrgCodeAndRoleCode(String orgCode, String roleCode);
}
