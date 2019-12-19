package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.model.bo.CrudOrganizationBO;
import cn.jarod.bluecat.auth.repository.OrganizationRepository;
import cn.jarod.bluecat.auth.service.OrganizationService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.TreeModel;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.core.utils.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/10/16
 */
@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * 保存组织数据
     * @param orgBO
     * @return
     */
    @Override
    public OrganizationDO saveOrganization(CrudOrganizationBO orgBO) {
        orgBO.reset();
        OrganizationDO orgDO = organizationRepository.findByOrgCode(orgBO.getNode()).orElse(new OrganizationDO());
        orgDO.setOrgCode(orgBO.getNode());
        orgDO.setParentCode(orgBO.getPNode());
        orgDO.setModifier(orgBO.getModifier());
        orgDO.setCreator(orgBO.getModifier());
        BeanHelperUtil.copyNotNullProperties(orgBO,orgDO);
        return organizationRepository.save(orgDO);
    }

    /**
     * 删除Org
     * @param orgBO
     */
    @Override
    public void delOrganization(CrudOrganizationBO orgBO) {
        organizationRepository.delete(organizationRepository.findByOrgCode(orgBO.getNode()).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 根据OrgCode查询Org
     * @param orgCode
     * @return
     */
    @Override
    public CrudOrganizationBO findOneByOrgCode(String orgCode) {
        CrudOrganizationBO orgDTO = new CrudOrganizationBO();
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
    public List<TreeModel> findOrgTreeByFullCode(String fullCode) {
        List<CrudOrganizationBO> list = organizationRepository.findAllByFullCodeLike(fullCode+ Const.SQL_LIKE).stream().map(e->{
            CrudOrganizationBO orgDTO = new CrudOrganizationBO();
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNode(e.getOrgCode());
            orgDTO.setPNode(e.getParentCode());
            return orgDTO;
        }).collect(Collectors.toList());
        log.info(JSON.toJSONString(list));
        return TreeUtil.getTree(list);
    }

    /**
     * 根据编号和系统编码获取组织 散列表
     * @param codes
     * @return
     */
    @Override
    public Map<String, OrganizationDO> queryOrgMapByCodesAndSys(List<String> codes, String sys) {
        return organizationRepository.findAllBySysCodeInAndOrgCodeIn(Lists.newArrayList(Const.SYS_ROOT,sys), codes).stream().collect(Collectors.toMap(OrganizationDO::getOrgCode, Function.identity()));
    }

}

