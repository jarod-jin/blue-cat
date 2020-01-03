package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseNoteBO;
import cn.jarod.bluecat.resource.model.dto.QueryReleaseDTO;
import org.springframework.data.domain.Page;

/**
 * @author jarod.jin 2019/11/20
 */
public interface ApplicationService {

    /**
     * 查询系统版本
     * @param queryDTO 查询对象
     * @return Page
     */
//    Page<ReleaseDO> queryPage(QueryReleaseDTO queryDTO);

    /**
     * 保存系统版本
     * @param applicationBO 版本
     * @return ApplicationDO
     */
    ApplicationDO createApplicationNote(CrudApplicationBO applicationBO);

}
