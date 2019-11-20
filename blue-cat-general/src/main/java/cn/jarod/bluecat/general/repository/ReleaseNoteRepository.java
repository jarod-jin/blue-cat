package cn.jarod.bluecat.general.repository;

import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ReleaseNoteRepository extends JpaRepository<ReleaseNoteDO,Long> {

    List<ReleaseNoteDO> findAllByTerminalTypeAndReleaseVersionOrderByReleaseNoDesc(String terminalType,String releaseVersion);

}
