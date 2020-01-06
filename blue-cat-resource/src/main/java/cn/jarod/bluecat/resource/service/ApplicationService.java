package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;

/**
 * @author jarod.jin 2019/11/20
 */
public interface ApplicationService {

    /**
     * 查询系统
     * @param queryDTO 查询对象
     * @return Page
     */
//    Page<ReleaseDO> queryPage(QueryReleaseDTO queryDTO);

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
