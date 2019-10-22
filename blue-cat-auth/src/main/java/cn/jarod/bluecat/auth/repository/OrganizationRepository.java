package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface OrganizationRepository extends JpaRepository<OrganizationDO,Long> {

    Optional<OrganizationDO> findByOrgCode(String orgCode);
}
