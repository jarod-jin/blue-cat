package cn.jarod.bluecat.estimate.repository;

import cn.jarod.bluecat.estimate.entity.ContractItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ContractItemRepository extends JpaRepository<ContractItemDO,Long> {

    List<ContractItemDO> findAllBySerialNoAndSysCodeOrderByItemNoAsc(String serialNo, String sysCode);
}
