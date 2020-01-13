package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.HomePageDO;
import cn.jarod.bluecat.resource.entity.ResourceDO;
import cn.jarod.bluecat.resource.model.bo.element.CrudHomePageBO;
import cn.jarod.bluecat.resource.repository.HomePageRepository;
import cn.jarod.bluecat.resource.service.HomePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
@Slf4j
@Service
public class HomePageServiceImpl implements HomePageService {

    private final HomePageRepository homePageRepository;

    public HomePageServiceImpl(HomePageRepository homePageRepository) {
        this.homePageRepository = homePageRepository;
    }

    @Override
    public HomePageDO create(CrudHomePageBO homePageBO) {
        return homePageRepository.insert(BeanHelperUtil.createCopyBean(homePageBO, HomePageDO.class));
    }

    @Override
    public List<ResourceDO> findAllByAppId(String appId, String resourceType) {
        return null;
    }
}
