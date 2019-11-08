package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OrgRoleDO;
import cn.jarod.bluecat.auth.model.bo.LinkOrgRoleBO;
import cn.jarod.bluecat.auth.model.bo.RequestAuthorityBO;
import cn.jarod.bluecat.auth.repository.OrgRoleRepository;
import cn.jarod.bluecat.auth.service.IOrgRoleService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2019/11/4
 */
@Service
public class OrgRoleService implements IOrgRoleService {

    @Autowired
    private OrgRoleRepository orgRoleRepository;

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
            throw new BaseException(ReturnCode.S401);
        orgRoleDO.setCreator(linkOrgRoleBO.getOperator());
        orgRoleDO.setModifier(linkOrgRoleBO.getOperator());
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
        orgRoleRepository.delete(orgRoleRepository.findOne(Example.of(orgRoleDO)).orElseThrow(()->new BaseException(ReturnCode.D400)));
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
    public List<RequestAuthorityBO> queryOrgRoleByIds(List<Long> ids) {
        return orgRoleRepository.findAllById(ids).stream().map( e->{
            RequestAuthorityBO authorityBO = new RequestAuthorityBO();
            authorityBO.setOrgCode(e.getOrgCode());
            authorityBO.setRoleCode(e.getRoleCode());
            return authorityBO;
        }).collect(Collectors.toList());
    }

}
