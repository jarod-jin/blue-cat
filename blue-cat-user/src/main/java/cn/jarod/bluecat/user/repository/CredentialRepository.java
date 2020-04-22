package cn.jarod.bluecat.user.repository;

import cn.jarod.bluecat.user.entity.CredentialDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface CredentialRepository extends JpaRepository<CredentialDO,Long> {

    /**
     * 通过用户名查询认证对象
     * @param username 用户名
     * @return Optional
     */
    Optional<CredentialDO> findByUsername(String username);

}
