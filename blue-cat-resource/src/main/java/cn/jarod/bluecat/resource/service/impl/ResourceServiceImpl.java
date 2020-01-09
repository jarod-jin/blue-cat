package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.ResourceDO;
import cn.jarod.bluecat.resource.model.bo.CrudResourceBO;
import cn.jarod.bluecat.resource.repository.ResourceRepository;
import cn.jarod.bluecat.resource.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResourceDO create(CrudResourceBO resourceBO) {
        return resourceRepository.insert(BeanHelperUtil.createCopyBean(resourceBO,ResourceDO.class));
    }

    @Override
    public List<ResourceDO> findAllByAppId(String appId, String resourceType) {
        return null;
    }
}
