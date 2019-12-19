package cn.jarod.bluecat.general.service;

import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import cn.jarod.bluecat.general.model.bo.CrudReleaseNoteBO;
import cn.jarod.bluecat.general.model.dto.QueryReleaseDTO;
import org.springframework.data.domain.Page;

/**
 * @author jarod.jin 2019/11/20
 */
public interface ReleaseNoteService {

    /**
     * 查询系统版本
     * @param queryDTO 查询对象
     * @return Page
     */
    Page<ReleaseNoteDO> queryPage(QueryReleaseDTO queryDTO);

    /**
     * 保存系统版本
     * @param releaseNote 版本
     * @return ReleaseNoteDO
     */
    ReleaseNoteDO saveReleaseNote(CrudReleaseNoteBO releaseNote);

}
