package cn.jarod.bluecat.access.role.service;

import cn.jarod.bluecat.access.role.pojo.entity.RolePO;
import cn.jarod.bluecat.access.role.pojo.CrudRoleBO;
import cn.jarod.bluecat.access.role.repository.RoleRepository;
import cn.jarod.bluecat.core.api.pojo.QueryDO;
import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.common.utils.BeanHelperUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /**
     * 保存一个角色
     * @param roleBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RolePO saveRole(CrudRoleBO roleBO) {
        roleBO.reset();
        RolePO rolePO = roleRepository.findByRoleCode(roleBO.getRoleCode()).orElse(new RolePO());
        rolePO.setModifier(roleBO.getModifier());
        rolePO.setCreator(roleBO.getModifier());
        BeanHelperUtil.copyNotNullProperties(roleBO, rolePO);
        return roleRepository.save(rolePO);
    }

    /**
     * 删除一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRole(CrudRoleBO dto) {
        roleRepository.delete(roleRepository.findByRoleCode(dto.getRoleCode()).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 根据Code列表返回对应的散列表
     * @param codes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, RolePO> findRoleMapByCodes(List<String> codes, String sys) {
        return roleRepository.findAllByBelongToInAndRoleCodeIn(Lists.newArrayList(Constant.Common.SYS_ROOT,sys),codes).stream().collect(Collectors.toMap(RolePO::getRoleCode, Function.identity()));
    }

    /**
     * 根据条件查询角色Page
     * @param qo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RolePO> findRolePage(QueryDO qo) {
        return roleRepository.findAll(qo.getPageRequest());
    }

//    /**
//     * 查询指定id列表中的角色返回对应对应角色位置
//     * @param ids
//     * @return
//     */
//    @Override
//    @Transactional(readOnly = true)
//    public List<UserAuthority> findOrgRoleByIds(List<Long> ids) {
//        return orgRoleRepository.findAllById(ids).stream().map( e->{
//            UserAuthority authorityBO = new UserAuthority();
//            authorityBO.setScopeType(e());
//            authorityBO.setRoleCode(e.getRoleCode());
//            return authorityBO;
//        }).collect(Collectors.toList());
//    }



}
