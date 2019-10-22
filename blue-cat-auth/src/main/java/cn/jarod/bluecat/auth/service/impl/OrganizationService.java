package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.auth.repository.OrganizationRepository;
import cn.jarod.bluecat.auth.service.IOrganizationService;
import cn.jarod.bluecat.core.model.auth.UserDetailBO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther jarod.jin 2019/10/16
 */
@Slf4j
@Service
public class OrganizationService implements IOrganizationService {


    @Autowired
    private OrganizationRepository organizationRepository;

    /**
     * 保存组织数据
     * @param orgDTO
     * @return
     */
    @Override
    public OrganizationDTO saveOrganization(OrganizationDTO orgDTO, UserDetailBO user) {
        OrganizationDO orgDO = BeanHelperUtil.createCopyBean(orgDTO, OrganizationDO.class);
        orgDO.setOrgCode(orgDTO.getNode());
        orgDO.setParentCode(orgDTO.getPNode());
        organizationRepository.findByOrgCode(orgDO.getOrgCode());
        return null;
    }
}
