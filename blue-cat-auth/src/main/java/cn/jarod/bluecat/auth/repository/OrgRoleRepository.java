package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface OrgRoleRepository extends JpaRepository<OrgRoleDO,Long> {

    @Query(nativeQuery = true,value = "select r.* from (select distinct l.org_role_id as orid from user_location l where l.is_del = 0 " +
            "and l.username=:username) i, org_role r where r.is_del = 0 and r.id = i.orid")
    List<OrgRoleDO> findAllByUsername(@Param("username")String username);

    Optional<OrgRoleDO> findByOrgCodeAndRoleCode(String orgCode, String roleCode);
}
