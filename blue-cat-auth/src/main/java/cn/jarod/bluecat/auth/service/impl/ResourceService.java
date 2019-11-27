package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import cn.jarod.bluecat.auth.model.bo.LinkRoleResourceBO;
import cn.jarod.bluecat.auth.model.bo.QueryResourceTreeBO;
import cn.jarod.bluecat.auth.model.bo.CrudResourceBO;
import cn.jarod.bluecat.auth.repository.ResourceRepository;
import cn.jarod.bluecat.auth.repository.RoleResourceRepository;
import cn.jarod.bluecat.auth.service.IResourceService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2019/11/13
 */
@Service
public class ResourceService implements IResourceService {

    private static final String START_NO = "10001";

    private final ResourceRepository resourceRepository;

    private final RoleResourceRepository roleResourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, RoleResourceRepository roleResourceRepository) {
        this.resourceRepository = resourceRepository;
        this.roleResourceRepository = roleResourceRepository;
    }

    /**
     * 保存资源
     * @param resourceBO
     * @return
     */
    @Override
    @Transactional
    public ResourceDO saveResource(CrudResourceBO resourceBO) {
        resourceBO.clearId();
        if (StringUtils.isEmpty(resourceBO.getResourceCode()))
            createResourceCode(resourceBO);
        ResourceDO resourceDO = resourceRepository.findByResourceCode(resourceBO.getResourceCode()).orElse(new ResourceDO());
        resourceDO.setModifier(resourceBO.getOperator());
        resourceDO.setCreator(resourceBO.getOperator());
        BeanHelperUtil.copyNotNullProperties(resourceBO,resourceDO);
        return resourceRepository.save(resourceDO);
    }

    /**
     * 创建ResourceCode
     * @param resourceBO
     */
    private void createResourceCode(CrudResourceBO resourceBO) {
        String codePrefix = resourceBO.getSysCode().substring(0,2).toUpperCase();
        String codeNo = resourceRepository.findMaxResourceCodeBySys(codePrefix, resourceBO.getSysCode());
        resourceBO.setResourceCode(codePrefix + (StringUtils.hasText(codeNo)? Integer.parseInt(codeNo)+1 : START_NO));
    }

    /**
     * 删除资源
     * @param resourceBO
     */
    @Override
    @Transactional
    public void delResource(CrudResourceBO resourceBO) {
        RoleResourceDO roleResourceDO = new RoleResourceDO();
        roleResourceDO.setResourceCode(resourceBO.getResourceCode());
        if (roleResourceRepository.exists(Example.of(roleResourceDO)))
            throw new BaseException(ReturnCode.D400.getCode(),"存在绑定权限，无法删除资源");
        resourceRepository.delete(resourceRepository.findByResourceCode(resourceBO.getResourceCode()).orElseThrow(()->new BaseException(ReturnCode.D400)));
    }

    /**
     * 保存对象
     * @param linkBO
     * @return
     */
    @Override
    @Transactional
    public RoleResourceDO saveRoleResource(LinkRoleResourceBO linkBO){
        RoleResourceDO roleResourceDO = new RoleResourceDO();
        roleResourceDO.setResourceCode(linkBO.getResourceCode());
        roleResourceDO.setRoleCode(linkBO.getRoleCode());
        if (roleResourceRepository.exists(Example.of(roleResourceDO)))
            throw new BaseException(ReturnCode.S400);
        roleResourceDO.setModifier(linkBO.getOperator());
        roleResourceDO.setCreator(linkBO.getOperator());
        return roleResourceRepository.save(roleResourceDO);
    }

    /**
     * 删除角色资源对应关系
     * @param linkBO
     */
    @Override
    @Transactional
    public void delRoleResource(LinkRoleResourceBO linkBO) {
        roleResourceRepository.delete(roleResourceRepository.findByResourceCodeAndRoleCode(linkBO.getResourceCode(),linkBO.getRoleCode())
                .orElseThrow(()->new BaseException(ReturnCode.D400)));
    }

    /**
     * 根据角色编号检查是否有关联数据，删除角色时判断使用
     * @param roleCode
     * @return
     */
    @Override
    public boolean hasLinkByRoleCode(String roleCode) {
        RoleResourceDO roleResourceDO = new RoleResourceDO();
        roleResourceDO.setRoleCode(roleCode);
        return roleResourceRepository.exists(Example.of(roleResourceDO));
    }


    /**
     * 根据系统和对应的角色，查询所有资源
     * @param sys
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<QueryResourceTreeBO> queryResourceTreeBySysAndRoleCodes(String sys, List<String> roleCodes) {
        Set<String> resourceSets = roleResourceRepository.findAllByRoleCodeIn(roleCodes).stream().map(RoleResourceDO::getResourceCode).collect(Collectors.toSet());
        return resourceRepository.findAllBySysCodeOrderByDisOrder(sys).stream().map(e->{
            QueryResourceTreeBO qrtBO = BeanHelperUtil.createCopyBean(e,QueryResourceTreeBO.class);
            qrtBO.setNode(e.getResourceCode());
            qrtBO.setPNode(e.getParentCode());
            qrtBO.setAccess(resourceSets.contains(e.getResourceCode()));
            return qrtBO;
        }).collect(Collectors.toList());
    }
}
