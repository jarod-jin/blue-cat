package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface AuthorityInfoRepository extends JpaRepository<AuthorityInfoDO,Long> {

    Optional<AuthorityInfoDO> findByAuthority(String authority);
}
