package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ResourceRepository extends JpaRepository<ResourceDO,Long> {

    Optional<ResourceDO> findByResourceCode(String resourceCode);

    List<ResourceDO> findAllBySysCodeAndResourceCodeIn(String sys, List<String> codes);

    @Query(value = "select (cast(substring_index(resource_code, :prefix, -1) as unsigned)) as a from sys_resource where sys_code =:sysCode order by a desc limit 1",
            nativeQuery = true)
    String findMaxResourceCodeBySys(@Param("prefix")String prefix, @Param("sysCode")String sysCode);
}
