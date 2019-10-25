package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;

/**
 * @auther jarod.jin 2019/10/16
 */
public interface IOrganizationService {

    OrganizationDTO saveOrganization(OrganizationDTO orgDTO);

    void delOrganization(OrganizationDTO orgDTO);
}
