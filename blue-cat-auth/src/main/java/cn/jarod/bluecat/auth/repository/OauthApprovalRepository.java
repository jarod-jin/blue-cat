package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.OauthApprovalDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/2
 */
public interface OauthApprovalRepository extends JpaRepository<OauthApprovalDO,Long> {
}
