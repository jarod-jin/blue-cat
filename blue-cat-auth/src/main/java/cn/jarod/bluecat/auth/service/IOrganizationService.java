package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.bo.CrudOrganizationBO;
import cn.jarod.bluecat.core.model.TreeModel;

import java.util.List;
import java.util.Map;

/**
 * @auther jarod.jin 2019/10/16
 */
public interface IOrganizationService {

    OrganizationDO saveOrganization(CrudOrganizationBO orgBO);

    void delOrganization(CrudOrganizationBO orgBO);

    CrudOrganizationBO findOneByOrgCode(String orgCode);

    List<TreeModel> findOrgTreeByFullCode(String fullCode);

    Map<String, OrganizationDO> queryOrgMapByCodesAndSys(List<String> codes,String sys);
}
