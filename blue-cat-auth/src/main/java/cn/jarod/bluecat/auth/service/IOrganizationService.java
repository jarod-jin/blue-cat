package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.core.model.auth.UserDetailBO;

/**
 * @auther jarod.jin 2019/10/16
 */
public interface IOrganizationService {

    OrganizationDTO saveOrganization(OrganizationDTO orgDTO, UserDetailBO user);
}
