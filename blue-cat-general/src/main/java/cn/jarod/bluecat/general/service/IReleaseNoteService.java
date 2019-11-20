package cn.jarod.bluecat.general.service;

import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import cn.jarod.bluecat.general.model.bo.SaveReleaseNoteBO;
import cn.jarod.bluecat.general.model.dto.QueryReleaseDTO;
import org.springframework.data.domain.Page;

/**
 * @auther jarod.jin 2019/11/20
 */
public interface IReleaseNoteService {

    Page<ReleaseNoteDO> queryPage(QueryReleaseDTO queryDTO);

    ReleaseNoteDO saveReleaseNote(SaveReleaseNoteBO releaseNote);

}
