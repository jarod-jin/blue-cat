package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.core.model.TreeDTO;

import java.util.List;

/**
 * @auther jarod.jin 2019/10/16
 */
public interface IOrganizationService {

    OrganizationDTO saveOrganization(OrganizationDTO orgDTO);

    void delOrganization(OrganizationDTO orgDTO);

    OrganizationDTO findOneByOrgCode(String orgCode);

    List<TreeDTO> findOrgTreeByFullCode(String fullCode);
}
