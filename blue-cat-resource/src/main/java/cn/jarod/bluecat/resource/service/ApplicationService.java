package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.entity.ReleaseDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import cn.jarod.bluecat.resource.model.dto.ApplicationQuery;
import cn.jarod.bluecat.resource.model.dto.QueryReleaseDTO;
import org.springframework.data.domain.Page;

/**
 * @author jarod.jin 2019/11/20
 */
public interface ApplicationService {

    /**
     * 查询系统
     * @return List<ApplicationDO>
     */
    Page<ApplicationDO> queryApplication(ApplicationQuery query);

    /**
     * 查询
     * @param queryDTO 查询对象
     * @return Page
     */
    Page<ReleaseDO> queryReleaseByPage(QueryReleaseDTO queryDTO);

    /**
     * 新建系统
     * @param applicationBO 版本
     * @return ApplicationDO
     */
    ApplicationDO createApplication(CrudApplicationBO applicationBO);

    /**
     * 修改系统
     * @param applicationBO 版本
     * @return ApplicationDO
     */
    ApplicationDO updateApplication(CrudApplicationBO applicationBO);


    /**
     * 修改系统
     * @param applicationBO
     */
    void delApplication(CrudApplicationBO applicationBO);

    /**
     * 增加版本
     * @param crudReleaseBO
     * @return
     */
    ApplicationDO addRelease(CrudReleaseBO crudReleaseBO);
}
