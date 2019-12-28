package cn.jarod.bluecat.resource.service.impl;


import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.MetaElementDO;
import cn.jarod.bluecat.resource.entity.ResourceShareDO;
import cn.jarod.bluecat.resource.model.bo.CrudResourceBO;
import cn.jarod.bluecat.resource.model.bo.LinkResourceShareBO;
import cn.jarod.bluecat.resource.model.bo.QueryResourceTreeBO;
import cn.jarod.bluecat.resource.repository.ResourceRepository;
import cn.jarod.bluecat.resource.repository.ResourceShareRepository;
import cn.jarod.bluecat.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/11/13
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final String START_NO = "10001";

    private final ResourceRepository resourceRepository;

    private final ResourceShareRepository resourceShareRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceShareRepository resourceShareRepository) {
        this.resourceRepository = resourceRepository;
        this.resourceShareRepository = resourceShareRepository;
    }

    /**
     * 保存资源
     * @param resourceBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetaElementDO saveResource(CrudResourceBO resourceBO) {
        resourceBO.reset();
        if (StringUtils.isEmpty(resourceBO.getResourceCode())){
            createResourceCode(resourceBO);}
        MetaElementDO metaElementDO = resourceRepository.findByResourceCode(resourceBO.getResourceCode()).orElse(new MetaElementDO());
        BeanHelperUtil.copyNotNullProperties(resourceBO, metaElementDO);
        metaElementDO.setCreator(resourceBO.getModifier());
        return resourceRepository.save(metaElementDO);
    }

    /**
     * 创建ResourceCode
     * @param resourceBO
     */
    private void createResourceCode(CrudResourceBO resourceBO) {
        String codePrefix = resourceBO.getBelongTo().substring(0,2).toUpperCase();
        String codeNo = resourceRepository.findMaxResourceCodeBySys(codePrefix, resourceBO.getBelongTo());
        resourceBO.setResourceCode(codePrefix + (StringUtils.hasText(codeNo)? Integer.parseInt(codeNo)+1 : START_NO));
    }

    /**
     * 删除资源
     * @param resourceBO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delResource(CrudResourceBO resourceBO) {
        ResourceShareDO resourceShareDO = new ResourceShareDO();
        resourceShareDO.setResourceCode(resourceBO.getResourceCode());
        if (resourceShareRepository.exists(Example.of(resourceShareDO))){
            throw new BaseException(ReturnCode.INVALID_REQUEST.getCode(),"存在绑定权限，无法删除资源");}
        resourceRepository.delete(resourceRepository.findByResourceCode(resourceBO.getResourceCode()).orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 保存对象
     * @param linkBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceShareDO saveResourceShare(LinkResourceShareBO linkBO){
        ResourceShareDO resourceShareDO = new ResourceShareDO(linkBO.getResourceCode(),linkBO.getShareCode(),linkBO.getShareType());
        if (resourceShareRepository.exists(Example.of(resourceShareDO))){
            throw new BaseException(ReturnCode.NOT_ACCEPTABLE);}
        resourceShareDO.setModifier(linkBO.getModifier());
        resourceShareDO.setCreator(linkBO.getModifier());
        return resourceShareRepository.save(resourceShareDO);
    }

    /**
     * 删除角色资源对应关系
     * @param linkBO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRoleResource(LinkResourceShareBO linkBO) {
        resourceShareRepository.delete(resourceShareRepository
                .findOne(Example.of(new ResourceShareDO(linkBO.getResourceCode(),linkBO.getShareCode(),linkBO.getShareType())))
                .orElseThrow(()->new BaseException(ReturnCode.GONE)));
    }

    /**
     * 根据角色编号检查是否有关联数据，删除角色时判断使用
     * @param roleCode
     * @param type
     * @return
     */
    @Override
    public boolean hasLinkByRoleCodeAndSys(String roleCode, Integer type) {
        ResourceShareDO roleResourceDO = new ResourceShareDO();
        roleResourceDO.setShareCode(roleCode);
        roleResourceDO.setShareType(type);
        return resourceShareRepository.exists(Example.of(roleResourceDO));
    }


    /**
     * 根据系统和对应的角色，查询所有资源
     * @param sys
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<QueryResourceTreeBO> findResourceTreeBySysAndRoleCodes(String sys, List<String> roleCodes) {
        Set<String> resourceSets = resourceShareRepository.findAllByShareCodeIn(roleCodes).stream().map(ResourceShareDO::getResourceCode).collect(Collectors.toSet());
        return resourceRepository.findAllByBelongToOrderBysortOrder(sys).stream().map(e->{
            QueryResourceTreeBO qrtBO = BeanHelperUtil.createCopyBean(e,QueryResourceTreeBO.class);
            qrtBO.setNodeId(e.getResourceCode());
            qrtBO.setParentId(e.getParentCode());
            qrtBO.setAccess(resourceSets.contains(e.getResourceCode()));
            return qrtBO;
        }).collect(Collectors.toList());
    }
}
