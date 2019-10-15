package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ResourceRepository extends JpaRepository<ResourceDO,Long> {

}
