package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.auth.service.IOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @auther jarod.jin 2019/10/16
 */
@Slf4j
@Service
public class OrganizationService implements IOrganizationService {
    /**
     * 保存组织数据
     * @param orgDTO
     * @return
     */
    @Override
    public OrganizationDTO saveOrganization(OrganizationDTO orgDTO) {
        return null;
    }
}
