package cn.jarod.bluecat.resource.repository;

import cn.jarod.bluecat.resource.entity.MetaElementDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ResourceRepository extends JpaRepository<MetaElementDO,Long> {

    /**
     * 根据资源编码查询资源
     * @param resourceCode 资源单号
     * @return Optional
     */
    Optional<MetaElementDO> findByResourceCode(String resourceCode);

    /**
     * 通过所属系统编号和前缀查询最大的单据号
     *
     * @param prefix  前缀
     * @param belongTo 所属系统编号
     * @return String
     */
    @Query(value = "select (cast(substring_index(resource_code, :prefix, -1) as unsigned)) as a from sys_resource where sys_code =:belongTo order by a desc limit 1",
            nativeQuery = true)
    String findMaxResourceCodeBySys(@Param("prefix") String prefix, @Param("belongTo") String belongTo);

    /**
     * 根所属系统编号查询所有该系统下所有资源列表
     * @param sys 所属系统编号
     * @return List
     */
    List<MetaElementDO> findAllByBelongToOrderBysortOrder(String sys);
}
