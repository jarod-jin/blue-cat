package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.ApplicationDO;
import cn.jarod.bluecat.resource.entity.ReleaseDO;
import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import cn.jarod.bluecat.resource.model.dto.ApplicationQuery;
import cn.jarod.bluecat.resource.repository.ApplicationRepository;
import cn.jarod.bluecat.resource.service.ApplicationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;



/**
 * @author jarod.jin 2019/11/20
 */
@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * 根据查询
     * @param query
     * @return Page
     */
    @Override
    public Page<ApplicationDO> findAllApplication(ApplicationQuery query) {
        ApplicationDO application = new ApplicationDO();
        application.setDescription(query.getDescription());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues();
        return applicationRepository.findAll(Example.of(application, matcher), query.getPageRequest());
    }

    @Override
    public ReleaseDO findLatestRelease(ObjectId id, String type) {
        ApplicationDO applicationDO = applicationRepository.findById(id).orElseThrow(ApiResultUtil::fail4NotFound);
        if (!applicationDO.getReleases().containsKey(type)){
            throw ApiResultUtil.fail4NotFound();
        }
        return applicationDO.getReleases().get(type).stream().max(Comparator.comparing(ReleaseDO::getBuildNo))
                .orElseThrow(()->ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE));
    }

    /**
     * 新建应用
     * @param applicationBO 应用对象
     * @return ApplicationDO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplicationDO createApplication(CrudApplicationBO applicationBO) {
        if (applicationBO.getId()!=null && applicationRepository.existsById(applicationBO.getId())){
            throw ApiResultUtil.fail4Existed();
        }
        applicationBO.setId(null);
        ApplicationDO applicationDO = new ApplicationDO();
        BeanHelperUtil.copyNullProperties(applicationBO, applicationDO);
        applicationDO.setGmtCreate(LocalDateTime.now());
        applicationDO.setGmtModified(LocalDateTime.now());
        applicationDO.setCreator(applicationBO.getModifier());
        return applicationRepository.insert(applicationDO);
    }

    /**
     * 修改应用
     * @param applicationBO 应用对象
     * @return ApplicationDO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplicationDO updateApplication(CrudApplicationBO applicationBO) {
        if (applicationBO.getId()==null){
            throw ApiResultUtil.fail4BadParameter(ReturnCode.UNPROCESSABLE_ENTITY);
        }
        ApplicationDO applicationDO =applicationRepository.findById(applicationBO.getId())
                .orElseThrow(ApiResultUtil::fail4NotFound);
        BeanHelperUtil.copyNotNullProperties(applicationBO, applicationDO);
        applicationDO.setGmtModified(LocalDateTime.now());
        return applicationRepository.save(applicationDO);
    }

    /**
     * 删除应用
     * @param applicationBO 应用对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delApplication(CrudApplicationBO applicationBO) {
        if (applicationBO.getId()==null){
            throw  ApiResultUtil.fail4BadParameter(ReturnCode.UNPROCESSABLE_ENTITY);
        }
        ApplicationDO applicationDO =applicationRepository.findById(applicationBO.getId())
                .orElseThrow(ApiResultUtil::fail4NotFound);
        applicationRepository.delete(applicationDO);
    }

    /**
     * 修改版本数据
     * @param crudReleaseBO
     * @return ApplicationDO
     */
    @Override
    public ApplicationDO addRelease(CrudReleaseBO crudReleaseBO) {
        if (crudReleaseBO.getAppId()==null){
            throw  ApiResultUtil.fail4BadParameter(ReturnCode.UNPROCESSABLE_ENTITY);
        }
        ApplicationDO applicationDO =applicationRepository.findById(crudReleaseBO.getAppId())
                .orElseThrow(ApiResultUtil::fail4NotFound);
        addRelease2Application(applicationDO,crudReleaseBO);
        applicationDO.setVersion(crudReleaseBO.getVersion());
        applicationDO.setGmtModified(LocalDateTime.now());
        return applicationRepository.save(applicationDO);
    }

    /**
     * 增加版本数据
     * @param applicationDO
     * @param crudReleaseBO
     * @return ApplicationDO
     */
    private void addRelease2Application(ApplicationDO applicationDO, CrudReleaseBO crudReleaseBO){
        if (crudReleaseBO == null){
            return;
        }
        ReleaseDO tmpRelease = BeanHelperUtil.createCopyBean(crudReleaseBO,ReleaseDO.class);
        Map<String,List<ReleaseDO>> releaseListMap = (applicationDO.getReleases()==null) ? Maps.newTreeMap():applicationDO.getReleases();
        List<ReleaseDO> releaseList = releaseListMap.get(crudReleaseBO.getReleaseType());
        releaseListMap.put(crudReleaseBO.getReleaseType(),margeRelease2List(releaseList, tmpRelease));
        applicationDO.setReleases(releaseListMap);
    }

    /**
     * 加入release列表
     * @param doList
     * @param release
     */
    private List<ReleaseDO> margeRelease2List(List<ReleaseDO> doList, ReleaseDO release){
        List<ReleaseDO> tmpList = (doList==null)?Lists.newArrayList():doList;
        if (tmpList.stream().noneMatch(d-> {
            if (d.getReleaseVersion().equals(release.getReleaseVersion())){
                d.setBuildNo(release.getBuildNo());
                d.setNotes(release.getNotes());
                return true;
            }
            return false;
        })) {
            tmpList.add(release);
        }
        return tmpList;
    }
}
