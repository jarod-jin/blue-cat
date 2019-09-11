package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.CredentialDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface CredentialRepository extends JpaRepository<CredentialDO,Long> {

    Optional<CredentialDO> findByAuthority(String authority);

}
