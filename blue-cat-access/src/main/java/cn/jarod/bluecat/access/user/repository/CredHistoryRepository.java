package cn.jarod.bluecat.access.user.repository;

import cn.jarod.bluecat.access.user.entity.CredHistoryDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jarod.jin 2019/9/9
 */
public interface CredHistoryRepository extends JpaRepository<CredHistoryDO,Long> {

    /**
     * 通过用户民查询密码历史记录
     * @param username 用户名
     * @return List
     */
    List<CredHistoryDO> findAllByUsername(String username);

    /**
     * 用户名密码是否存在
     * @param username 用户名
     * @param modifiedPassword  密码
     * @return boolean
     */
    boolean existsByUsernameAndPassword(String username, String modifiedPassword);
}

