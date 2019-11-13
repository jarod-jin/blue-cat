package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.ResourceDO;
import cn.jarod.bluecat.auth.model.bo.SaveResourceBO;
import cn.jarod.bluecat.auth.repository.ResourceRepository;
import cn.jarod.bluecat.auth.service.IResourceService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/13
 */
@Service
public class ResourceService implements IResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * 保存资源
     * @param resourceBO
     * @return
     */
    @Override
    @Transactional
    public ResourceDO saveResource(SaveResourceBO resourceBO) {
        resourceBO.clearId();
        ResourceDO resourceDO = resourceRepository.findByResourceCode(resourceBO.getResourceCode()).orElse(new ResourceDO());
        resourceDO.setModifier(resourceBO.getOperator());
        resourceDO.setCreator(resourceBO.getOperator());
        BeanHelperUtil.copyNotNullProperties(resourceBO,resourceDO);
        return resourceRepository.save(resourceDO);
    }

    /**
     * 删除资源
     * @param resourceBO
     */
    @Override
    @Transactional
    public void delResource(SaveResourceBO resourceBO) {
        resourceRepository.delete(resourceRepository.findByResourceCode(resourceBO.getResourceCode()).orElseThrow(()->new BaseException(ReturnCode.D400)));
    }


    /**
     *
     * @param codes
     * @param sys
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResourceDO> queryResourceListByCodes(List<String> codes, String sys) {
        return resourceRepository.findAllBySysCodeAndResourceCodeIn(sys,codes);
    }
}
