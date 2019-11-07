package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.dto.OrganizationDTO;
import cn.jarod.bluecat.auth.repository.OrganizationRepository;
import cn.jarod.bluecat.auth.service.IOrganizationService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.TreeDTO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.core.utils.TreeUtil;
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
    public OrganizationDO saveOrganization(OrganizationDTO orgDTO) {
        orgDTO.clearId();
        OrganizationDO orgDO = organizationRepository.findByOrgCode(orgDTO.getNode()).orElse(new OrganizationDO());
        orgDO.setOrgCode(orgDTO.getNode());
        orgDO.setParentCode(orgDTO.getPNode());
        orgDO.setModifier(orgDTO.getOperator());
        orgDO.setCreator(orgDTO.getOperator());
        BeanHelperUtil.copyNotNullProperties(orgDTO,orgDO);
        return organizationRepository.save(orgDO);
    }

    /**
     * 删除Org
     * @param orgDTO
     */
    @Override
    public void delOrganization(OrganizationDTO orgDTO) {
        organizationRepository.delete(organizationRepository.findByOrgCode(orgDTO.getNode()).orElseThrow(()->new BaseException(ReturnCode.D400)));
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

    /**
     * 根据当前部门节点查询下属所有部门树
     * @param fullCode
     * @return
     */
    @Override
    public List<TreeDTO> findOrgTreeByFullCode(String fullCode) {
        List<OrganizationDTO> list = organizationRepository.findAllByFullCodeLike(fullCode+ Const.SQL_LIKE).stream().map(e->{
            OrganizationDTO orgDTO = new OrganizationDTO();
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNode(e.getOrgCode());
            orgDTO.setPNode(e.getParentCode());
            return orgDTO;
        }).collect(Collectors.toList());
        log.info(JSON.toJSONString(list));
        return TreeUtil.getTree(list);
    }




}
