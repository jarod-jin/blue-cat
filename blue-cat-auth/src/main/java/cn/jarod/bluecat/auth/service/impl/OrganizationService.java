package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.auth.repository.OrganizationRepository;
import cn.jarod.bluecat.auth.service.IOrganizationService;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public OrganizationDTO saveOrganization(OrganizationDTO orgDTO) {
        OrganizationDO orgDO = BeanHelperUtil.createCopyBean(orgDTO, OrganizationDO.class);
        orgDO.setOrgCode(orgDTO.getNode());
        orgDO.setParentCode(orgDTO.getPNode());
        orgDO.setModifier(orgDTO.getOperator());
        orgDO.setCreator(orgDTO.getOperator());
        organizationRepository.findByOrgCode(orgDO.getOrgCode()).ifPresent(
                e -> BeanHelperUtil.copyNullProperties(e,orgDO)
        );
        OrganizationDO result = organizationRepository.save(orgDO);
        orgDTO = BeanHelperUtil.createCopyBean(result, OrganizationDTO.class);
        orgDTO.setNode(result.getOrgCode());
        orgDTO.setPNode(result.getParentCode());
        return orgDTO;
    }

    /**
     * 删除Org
     * @param orgDTO
     */
    @Override
    public void delOrganization(OrganizationDTO orgDTO) {
        organizationRepository.findByOrgCode(orgDTO.getNode()).ifPresent(e -> organizationRepository.delete(e));
    }

    /**
     * 根据OrgCode查询Org
     * @param orgCode
     * @return
     */
    @Override
    public OrganizationDTO findOneByOrgCode(String orgCode) {
        OrganizationDTO orgDTO = new OrganizationDTO();
        organizationRepository.findByOrgCode(orgDTO.getNode()).ifPresent(e -> {
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNode(e.getOrgCode());
            orgDTO.setPNode(e.getParentCode());
        });
        return orgDTO;
    }

    @Override
    public List<OrganizationDTO> findOrgTreeByFullCode(String fullCode) {
        List<OrganizationDTO> list = organizationRepository.findAllByFullCodeLike(fullCode+ Const.SQL_LIKE).stream().map(e->{
            OrganizationDTO orgDTO = new OrganizationDTO();
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNode(e.getOrgCode());
            orgDTO.setPNode(e.getParentCode());
            return orgDTO;
        }).collect(Collectors.toList());
        log.info(JSON.toJSONString(list));
        return list;
    }
}
