package cn.jarod.bluecat.general.service;

import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import cn.jarod.bluecat.general.model.bo.CrudReleaseNoteBO;
import cn.jarod.bluecat.general.model.dto.QueryReleaseDTO;
import org.springframework.data.domain.Page;

/**
 * @author jarod.jin 2019/11/20
 */
public interface ReleaseNoteService {

    Page<ReleaseNoteDO> queryPage(QueryReleaseDTO queryDTO);

    ReleaseNoteDO saveReleaseNote(CrudReleaseNoteBO releaseNote);

}
