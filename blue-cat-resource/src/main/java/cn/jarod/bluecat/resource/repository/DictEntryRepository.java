package cn.jarod.bluecat.resource.repository;

import cn.jarod.bluecat.resource.entity.DictEntryDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface DictEntryRepository extends JpaRepository<DictEntryDO,Long> {

    Optional<DictEntryDO> findByDictCode(String dictCode);

}
