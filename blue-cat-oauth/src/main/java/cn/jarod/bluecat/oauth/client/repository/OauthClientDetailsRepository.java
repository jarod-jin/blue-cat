package cn.jarod.bluecat.oauth.client.repository;


import cn.jarod.bluecat.oauth.client.entity.OauthClientDetailsDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/2
 */
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetailsDO,Long> {

    /**
     * 通过ClientId查询对应的的OauthClient
     * @param clientId 客户端Id
     * @return Optional
     */
    Optional<OauthClientDetailsDO> findByClientId(String clientId);
}
