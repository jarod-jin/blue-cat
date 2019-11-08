package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.bo.SaveOrganizationBO;
import cn.jarod.bluecat.auth.repository.OrganizationRepository;
import cn.jarod.bluecat.auth.service.IOrganizationService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.TreeBO;
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
     * @param orgBO
     * @return
     */
    @Override
    public OrganizationDO saveOrganization(SaveOrganizationBO orgBO) {
        orgBO.clearId();
        OrganizationDO orgDO = organizationRepository.findByOrgCode(orgBO.getNode()).orElse(new OrganizationDO());
        orgDO.setOrgCode(orgBO.getNode());
        orgDO.setParentCode(orgBO.getPNode());
        orgDO.setModifier(orgBO.getOperator());
        orgDO.setCreator(orgBO.getOperator());
        BeanHelperUtil.copyNotNullProperties(orgBO,orgDO);
        return organizationRepository.save(orgDO);
    }

    /**
     * 删除Org
     * @param orgBO
     */
    @Override
    public void delOrganization(SaveOrganizationBO orgBO) {
        organizationRepository.delete(organizationRepository.findByOrgCode(orgBO.getNode()).orElseThrow(()->new BaseException(ReturnCode.D400)));
    }

    /**
     * 根据OrgCode查询Org
     * @param orgCode
     * @return
     */
    @Override
    public SaveOrganizationBO findOneByOrgCode(String orgCode) {
        SaveOrganizationBO orgDTO = new SaveOrganizationBO();
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
    public List<TreeBO> findOrgTreeByFullCode(String fullCode) {
        List<SaveOrganizationBO> list = organizationRepository.findAllByFullCodeLike(fullCode+ Const.SQL_LIKE).stream().map(e->{
            SaveOrganizationBO orgDTO = new SaveOrganizationBO();
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNode(e.getOrgCode());
            orgDTO.setPNode(e.getParentCode());
            return orgDTO;
        }).collect(Collectors.toList());
        log.info(JSON.toJSONString(list));
        return TreeUtil.getTree(list);
    }




}
