package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.HomePageDO;
import cn.jarod.bluecat.resource.model.bo.element.CrudHomePageBO;
import cn.jarod.bluecat.resource.model.dto.HomePageQuery;
import cn.jarod.bluecat.resource.repository.HomePageRepository;
import cn.jarod.bluecat.resource.service.HomePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        homePageBO.reset();
        HomePageDO homePageDO = BeanHelperUtil.createCopyBean(homePageBO, HomePageDO.class);
        homePageDO.setCreator(homePageBO.getModifier());
        homePageDO.setGmtCreate(LocalDateTime.now());
        homePageDO.setGmtModified(LocalDateTime.now());
        return homePageRepository.insert(homePageDO);
    }

    @Override
    public Page<HomePageDO> findAllByPage(HomePageQuery query) {
        HomePageDO homePageDO = BeanHelperUtil.createCopyBean(query, HomePageDO.class);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues();
        return homePageRepository.findAll(Example.of(homePageDO, matcher), query.getPageRequest());
    }


}
