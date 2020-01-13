package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.resource.entity.HomePageDO;
import cn.jarod.bluecat.resource.model.bo.element.CrudHomePageBO;
import cn.jarod.bluecat.resource.model.dto.HomePageQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public interface HomePageService {
    /**
     * 新建首页数据
     * @param resourceBO 资源数据
     * @return ResourceDO
     */
    HomePageDO create(CrudHomePageBO resourceBO);

    /**
     * 查询所有首页列表
     *  @param query 资源数据
     * @return
     */
    Page<HomePageDO> findAllByPage(HomePageQuery query);

}


