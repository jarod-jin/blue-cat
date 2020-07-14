package cn.jarod.bluecat.access.group.service;

import cn.jarod.bluecat.access.group.entity.GroupDO;
import cn.jarod.bluecat.access.group.pojo.CrudOrganizationBO;
import cn.jarod.bluecat.access.group.pojo.LinkOrgRoleBO;
import cn.jarod.bluecat.access.group.repository.GroupRepository;
import cn.jarod.bluecat.core.base.model.TreeModel;
import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.base.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.JsonUtil;
import cn.jarod.bluecat.core.utils.TreeUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/10/16
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository organizationRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * 保存组织数据
     * @param orgBO
     * @return
     */
    @Override
    public GroupDO saveOrganization(CrudOrganizationBO orgBO) {
        orgBO.reset();
        GroupDO orgDO = organizationRepository.findByGroupCode(orgBO.getNodeId()).orElse(new GroupDO());
        orgDO.setGroupCode(orgBO.getNodeId());
        orgDO.setParentCode(orgBO.getParentId());
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
        organizationRepository.delete(organizationRepository.findByGroupCode(orgBO.getNodeId()).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 根据OrgCode查询Org
     * @param orgCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public CrudOrganizationBO findOneByOrgCode(String orgCode) {
        CrudOrganizationBO orgDTO = new CrudOrganizationBO();
        organizationRepository.findByGroupCode(orgDTO.getNodeId()).ifPresent(e -> {
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNodeId(e.getGroupCode());
            orgDTO.setParentId(e.getParentCode());
        });
        return orgDTO;
    }

    /**
     * 根据当前部门节点查询下属所有部门树
     * @param fullCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TreeModel> findGroupTreeByFullCode(String fullCode) {
        List<CrudOrganizationBO> list = organizationRepository.findAllByFullCodeLike(fullCode+ Constant.Symbol.SQL_LIKE).stream().map(e->{
            CrudOrganizationBO orgDTO = new CrudOrganizationBO();
            BeanUtils.copyProperties(e,orgDTO);
            orgDTO.setNodeId(e.getGroupCode());
            orgDTO.setParentId(e.getParentCode());
            return orgDTO;
        }).collect(Collectors.toList());
        log.debug(JsonUtil.toJson(list));
        return TreeUtil.getTree(list);
    }


    @Override
    public List<String> findRoleCodesByGroup(LinkOrgRoleBO linkOrgRoleBO) {
        return null;
    }

    /**
     * 根据编号和所属系统编号获取组织 散列表
     * @param codes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, GroupDO> findGroupsByCodesAndSys(List<String> codes, String sys) {
        return organizationRepository.findAllByBelongToInAndOrgCodeIn(Lists.newArrayList(Constant.Common.SYS_ROOT,sys), codes).stream().collect(Collectors.toMap(GroupDO::getGroupCode, Function.identity()));
    }

}
