package cn.jarod.bluecat.estimate.repository;

import cn.jarod.bluecat.estimate.entity.ContractSheetDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ContractSheetRepository extends JpaRepository<ContractSheetDO,Long> {

    Optional<ContractSheetDO> findBySerialNoAndSysCode(String serialNo, String sysCode);
}