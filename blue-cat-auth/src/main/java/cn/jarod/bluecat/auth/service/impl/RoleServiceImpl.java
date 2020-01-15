package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.LinkOrgRoleBO;
import cn.jarod.bluecat.auth.model.bo.CrudRoleBO;
import cn.jarod.bluecat.auth.repository.OrgRoleRepository;
import cn.jarod.bluecat.auth.repository.RoleRepository;
import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.BaseQuery;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/11/4
 */
@Service
public class RoleServiceImpl implements cn.jarod.bluecat.auth.service.RoleService {

    private final RoleRepository roleRepository;

    private final OrgRoleRepository orgRoleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, OrgRoleRepository orgRoleRepository) {
        this.roleRepository = roleRepository;
        this.orgRoleRepository = orgRoleRepository;
    }


    /**
     * 保存一个角色
     * @param roleBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDO saveRole(CrudRoleBO roleBO) {
        roleBO.reset();
        RoleDO roleDO = roleRepository.findByRoleCode(roleBO.getRoleCode()).orElse(new RoleDO());
        roleDO.setModifier(roleBO.getModifier());
        roleDO.setCreator(roleBO.getModifier());
        BeanHelperUtil.copyNotNullProperties(roleBO,roleDO);
        return roleRepository.save(roleDO);
    }

    /**
     * 删除一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRole(CrudRoleBO dto) {
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setRoleCode(dto.getRoleCode());
        if (orgRoleRepository.exists(Example.of(orgRoleDO))) {
            throw new BaseException(ReturnCode.INVALID_REQUEST.getCode(),"存在绑定组织，无法删除角色");
        }
        roleRepository.delete(roleRepository.findByRoleCode(dto.getRoleCode()).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 根据Code列表返回对应的散列表
     * @param codes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String,RoleDO> findRoleMapByCodes(List<String> codes, String sys) {
        return roleRepository.findAllByBelongToInAndRoleCodeIn(Lists.newArrayList(Constant.Common.SYS_ROOT,sys),codes).stream().collect(Collectors.toMap(RoleDO::getRoleCode, Function.identity()));
    }

    /**
     * 根据条件查询角色Page
     * @param qo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoleDO> findRolePage(BaseQuery qo) {
        return roleRepository.findAll(qo.getPageRequest());
    }

    /**
     * 保存组织角色关系
     * @param linkOrgRoleBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrgRoleDO saveOrgRole(LinkOrgRoleBO linkOrgRoleBO){
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(linkOrgRoleBO.getOrgCode());
        orgRoleDO.setRoleCode(linkOrgRoleBO.getRoleCode());
        if (orgRoleRepository.exists(Example.of(orgRoleDO))) {
            throw ApiResultUtil.fail4Existed();
        }
        orgRoleDO.setCreator(linkOrgRoleBO.getModifier());
        orgRoleDO.setModifier(linkOrgRoleBO.getModifier());
        return orgRoleRepository.save(orgRoleDO);
    }


    /**
     * 删除一个角色
     * @param linkOrgRoleBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delOrgRole(LinkOrgRoleBO linkOrgRoleBO) {
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(linkOrgRoleBO.getOrgCode());
        orgRoleDO.setRoleCode(linkOrgRoleBO.getRoleCode());
        orgRoleRepository.delete(orgRoleRepository.findOne(Example.of(orgRoleDO)).orElseThrow(ApiResultUtil::fail4NotFound));
    }

    /**
     * 查询指定组织下的角色代码列表
     * @param linkOrgRoleBO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> findRoleCodesByOrg(LinkOrgRoleBO linkOrgRoleBO) {
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(linkOrgRoleBO.getOrgCode());
        return orgRoleRepository.findAll(Example.of(orgRoleDO)).stream().map(OrgRoleDO::getRoleCode).collect(Collectors.toList());
    }


    /**
     * 查询指定id列表中的角色返回对应对应角色位置
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserAuthority> findOrgRoleByIds(List<Long> ids) {
        return orgRoleRepository.findAllById(ids).stream().map( e->{
            UserAuthority authorityBO = new UserAuthority();
            authorityBO.setOrgCode(e.getOrgCode());
            authorityBO.setRoleCode(e.getRoleCode());
            return authorityBO;
        }).collect(Collectors.toList());
    }



}
