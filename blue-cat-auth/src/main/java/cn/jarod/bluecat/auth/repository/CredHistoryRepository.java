package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.CredHistoryDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface CredHistoryRepository extends JpaRepository<CredHistoryDO,Long> {

    List<CredHistoryDO> findAllByUsername(String username);

}
