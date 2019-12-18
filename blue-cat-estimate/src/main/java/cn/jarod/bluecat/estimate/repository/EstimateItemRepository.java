package cn.jarod.bluecat.estimate.repository;

import cn.jarod.bluecat.estimate.entity.EstimateItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jarod.jin 2019/9/9
 */
public interface EstimateItemRepository extends JpaRepository<EstimateItemDO,Long> {

    List<EstimateItemDO> findAllByEstimateSheetIdOrderByItemNoAsc(Long sheetId);
}

