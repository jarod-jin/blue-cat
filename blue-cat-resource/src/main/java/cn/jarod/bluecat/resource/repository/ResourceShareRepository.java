package cn.jarod.bluecat.resource.repository;

import cn.jarod.bluecat.resource.entity.ResourceShareDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ResourceShareRepository extends JpaRepository<ResourceShareDO,Long> {

    /**
     * 根据角色编码列表查询所有对应资源
     * @param shareCodes 分享列表
     * @return List
     */
    List<ResourceShareDO> findAllByShareCodeIn(List<String> shareCodes);
}


