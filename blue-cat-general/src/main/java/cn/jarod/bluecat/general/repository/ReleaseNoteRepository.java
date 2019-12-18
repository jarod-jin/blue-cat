package cn.jarod.bluecat.general.repository;

import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ReleaseNoteRepository extends JpaRepository<ReleaseNoteDO,Long> {

    Page<ReleaseNoteDO> findAllByTerminalTypeAndSysCode(String terminalType, String sysCode, Pageable pageable);

}
