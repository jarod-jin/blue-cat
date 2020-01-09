package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.entity.ReleaseDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import cn.jarod.bluecat.resource.model.dto.ApplicationQuery;
import cn.jarod.bluecat.resource.model.dto.QueryReleaseDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

/**
 * @author jarod.jin 2019/11/20
 */
public interface ApplicationService {

    /**
     * 查询系统
     * @param query 查询系统分页列表
     * @return List<ApplicationDO>
     */
    Page<ApplicationDO> findAllApplication(ApplicationQuery query);


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

    /**
     * 通过ObjectId查询最新的版本跟新内容
     * @param id ObjectId
     * @param type 终端类别
     * @return ReleaseDO
     */
    ReleaseDO findLatestRelease(ObjectId id, String type);
}
