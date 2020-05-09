package cn.jarod.bluecat.user.impl;

import cn.jarod.bluecat.user.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.bo.CrudOrganizationBO;

import java.util.List;
import java.util.Map;

/**
 * @author jarod.jin 2019/10/16
 */
public interface OrganizationService {


    /**
     * 修改组织
     * @param orgBO 组织crud模型
     * @return OrganizationDO
     */
    OrganizationDO saveOrganization(CrudOrganizationBO orgBO);

    /**
     * 删除组织
     * @param orgBO 删除组织对象
     */
    void delOrganization(CrudOrganizationBO orgBO);

    /**
     * 根据组织编码查询组织信息
     * @param orgCode 组织编码
     * @return CrudOrganizationBO
     */
    CrudOrganizationBO findOneByOrgCode(String orgCode);

    /**
     * 根据组织代码查询下属组织树
     * @param fullCode 组织代码
     * @return List
     */
    List<TreeModel> findOrgTreeByFullCode(String fullCode);

    /**
     * 根据组织代码查询相关组织信息，返回散列表
     * @param codes 组织编码列表
     * @param sys 所属系统编号
     * @return Map
     */
    Map<String, OrganizationDO> findOrgMapByCodesAndSys(List<String> codes, String sys);
}
