package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.LinkOrgRoleBO;
import cn.jarod.bluecat.auth.model.bo.CrudRoleBO;
import cn.jarod.bluecat.auth.repository.OrgRoleRepository;
import cn.jarod.bluecat.auth.repository.RoleRepository;
import cn.jarod.bluecat.auth.service.IRoleService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.BaseQO;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import org.assertj.core.util.Lists;
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
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    private final OrgRoleRepository orgRoleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, OrgRoleRepository orgRoleRepository) {
        this.roleRepository = roleRepository;
        this.orgRoleRepository = orgRoleRepository;
    }


    /**
     * 保存一个角色
     * @param roleBO
     * @return
     */
    @Override
    @Transactional
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
    @Transactional
    public void delRole(CrudRoleBO dto) {
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setRoleCode(dto.getRoleCode());
        if (orgRoleRepository.exists(Example.of(orgRoleDO)))
            throw new BaseException(ReturnCode.INVALID_REQUEST.getCode(),"存在绑定组织，无法删除角色");
        roleRepository.delete(roleRepository.findByRoleCode(dto.getRoleCode()).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 根据Code列表返回对应的散列表
     * @param codes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String,RoleDO> queryRoleMapByCodes(List<String> codes,String sys) {
        return roleRepository.findAllBySysCodeInAndRoleCodeIn(Lists.newArrayList(Const.SYS_ROOT,sys),codes).stream().collect(Collectors.toMap(RoleDO::getRoleCode, Function.identity()));
    }

    /**
     * 根据条件查询角色Page
     * @param qo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoleDO> queryRolePage(BaseQO qo) {
        Sort sort = new Sort(qo.isASC()? Sort.Direction.ASC:Sort.Direction.DESC, Const.CREATE_DATE);
        Pageable pageable = PageRequest.of(qo.getPageNum() - 1, qo.getPageCount(), sort);
        return roleRepository.findAll(pageable);
    }

    /**
     * 保存组织角色关系
     * @param linkOrgRoleBO
     * @return
     */
    @Override
    @Transactional
    public OrgRoleDO saveOrgRole(LinkOrgRoleBO linkOrgRoleBO){
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(linkOrgRoleBO.getOrgCode());
        orgRoleDO.setRoleCode(linkOrgRoleBO.getRoleCode());
        if (orgRoleRepository.exists(Example.of(orgRoleDO)))
            throw new BaseException(ReturnCode.NOT_FOUND);
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
    @Transactional
    public void delOrgRole(LinkOrgRoleBO linkOrgRoleBO) {
        OrgRoleDO orgRoleDO = new OrgRoleDO();
        orgRoleDO.setOrgCode(linkOrgRoleBO.getOrgCode());
        orgRoleDO.setRoleCode(linkOrgRoleBO.getRoleCode());
        orgRoleRepository.delete(orgRoleRepository.findOne(Example.of(orgRoleDO)).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 查询指定组织下的角色代码列表
     * @param linkOrgRoleBO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> queryRoleCodesByOrg(LinkOrgRoleBO linkOrgRoleBO) {
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
    public List<UserAuthority> queryOrgRoleByIds(List<Long> ids) {
        return orgRoleRepository.findAllById(ids).stream().map( e->{
            UserAuthority authorityBO = new UserAuthority();
            authorityBO.setOrgCode(e.getOrgCode());
            authorityBO.setRoleCode(e.getRoleCode());
            return authorityBO;
        }).collect(Collectors.toList());
    }



}
