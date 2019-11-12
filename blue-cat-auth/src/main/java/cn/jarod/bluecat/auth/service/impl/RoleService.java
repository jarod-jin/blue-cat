package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.SaveRoleBO;
import cn.jarod.bluecat.auth.repository.RoleRepository;
import cn.jarod.bluecat.auth.service.IRoleService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.BaseQO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2019/11/4
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 保存一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public RoleDO saveRole(SaveRoleBO dto) {
        dto.clearId();
        RoleDO roleDO = roleRepository.findByRoleCode(dto.getRoleCode()).orElse(new RoleDO());
        roleDO.setModifier(dto.getOperator());
        roleDO.setCreator(dto.getOperator());
        BeanHelperUtil.copyNotNullProperties(dto,roleDO);
        return roleRepository.save(roleDO);
    }

    /**
     * 删除一个角色
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public void delRole(SaveRoleBO dto) {
        roleRepository.delete(roleRepository.findByRoleCode(dto.getRoleCode()).orElseThrow(()->new BaseException(ReturnCode.D400)));
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


}
