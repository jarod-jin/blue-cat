package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;

import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.ApplicationDO;

import cn.jarod.bluecat.resource.model.bo.CrudApplicationBO;

import cn.jarod.bluecat.resource.repository.ApplicationRepository;
import cn.jarod.bluecat.resource.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
     * 新建应用
     * @param applicationBO
     * @return ApplicationDO
     */
    @Override
    public ApplicationDO createApplicationNote(CrudApplicationBO applicationBO) {
        if (applicationBO.getId()!=null && applicationRepository.existsById(applicationBO.getId())){
            throw new BaseException(ReturnCode.ALREADY_EXISTED); }
        applicationBO.setId(null);
        ApplicationDO applicationDO = new ApplicationDO();
        BeanHelperUtil.copyNullProperties(applicationBO, applicationDO);
        applicationDO.setCreator(applicationBO.getModifier());
        return applicationRepository.save(applicationDO);
    }
}
