package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.entity.ResourceDO;
import cn.jarod.bluecat.resource.model.bo.CrudResourceBO;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public interface ResourceService {
    /**
     * 新建资源数据
     * @param resourceBO 资源数据
     * @return ResourceDO
     */
    ResourceDO create(CrudResourceBO resourceBO);

    /**
     * 查询对应系统资源
     * @param appId
     * @param resourceType
     * @return
     */
    List<ResourceDO> findAllByAppId(String appId, String resourceType);
}
