package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ResourceRepository extends JpaRepository<ResourceDO,Long> {

    /**
     * 根据资源编码查询资源
     * @param resourceCode 资源单号
     * @return Optional
     */
    Optional<ResourceDO> findByResourceCode(String resourceCode);

    /**
     * 通过系统编码和前缀查询最大的单据号
     *
     * @param prefix  前缀
     * @param sysCode 系统编码
     * @return String
     */
    @Query(value = "select (cast(substring_index(resource_code, :prefix, -1) as unsigned)) as a from sys_resource where sys_code =:sysCode order by a desc limit 1",
            nativeQuery = true)
    String findMaxResourceCodeBySys(@Param("prefix") String prefix, @Param("sysCode") String sysCode);

    /**
     * 根系统编码查询所有该系统下所有资源列表
     * @param sys 系统编码
     * @return List
     */
    List<ResourceDO> findAllBySysCodeOrderByDisOrder(String sys);
}
