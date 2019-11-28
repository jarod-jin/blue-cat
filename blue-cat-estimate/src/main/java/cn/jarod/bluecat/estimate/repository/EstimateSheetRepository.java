package cn.jarod.bluecat.estimate.repository;

import cn.jarod.bluecat.estimate.entity.EstimateSheetDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface EstimateSheetRepository extends JpaRepository<EstimateSheetDO,Long> {

    Optional<EstimateSheetDO> findBySerialNoAndSysCodeAndUsernameAndFinishMark(String serialNo, String sysCode, String username, Integer notDel);

}

