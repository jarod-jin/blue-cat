package cn.jarod.bluecat.general.service.impl;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.general.entity.DictEntryDO;
import cn.jarod.bluecat.general.model.bo.UpdateDictEntryBO;
import cn.jarod.bluecat.general.model.bo.SaveDictEntryBO;
import cn.jarod.bluecat.general.repository.DictEntryRepository;
import cn.jarod.bluecat.general.service.IDictEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther jarod.jin 2019/11/19
 */
@Slf4j
@Service
public class DictEntryService implements IDictEntryService {

    @Autowired
    private DictEntryRepository dictEntryRepository;

    @Override
    public DictEntryDO queryByDictCode(String dictCode) {
        return dictEntryRepository.findByDictCode(dictCode).orElseThrow(()->new BaseException(ReturnCode.Q401));
    }

    /**
     * 新建修改字典
     * @param entryBO
     * @return
     */
    @Override
    @Transactional
    @TimeDiff
    public DictEntryDO saveDict(SaveDictEntryBO entryBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(entryBO.getDictCode()).orElse(new DictEntryDO());
        BeanHelperUtil.copyNotNullProperties(entryBO,entryDO);
        entryDO.setModifier(entryBO.getOperator());
        entryDO.setCreator(entryBO.getOperator());
        return dictEntryRepository.save(entryDO);
    }

    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional
    @TimeDiff
    public DictEntryDO modifyDictEntry(UpdateDictEntryBO modifyDictBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(modifyDictBO.getDictCode()).orElseThrow(()->new BaseException(ReturnCode.S400));
        entryDO.getEntryJson().putAll(modifyDictBO.getEntryJson());
        entryDO.setModifier(modifyDictBO.getOperator());
        if (modifyDictBO.getVersion()!=null)
            entryDO.setVersion(modifyDictBO.getVersion());
        return dictEntryRepository.save(entryDO);
    }

    /**
     * 删除字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional
    @TimeDiff
    public DictEntryDO delDictEntry(UpdateDictEntryBO modifyDictBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(modifyDictBO.getDictCode()).orElseThrow(()->new BaseException(ReturnCode.D400));
        modifyDictBO.getEntryJson().forEach((k,v)-> entryDO.getEntryJson().remove(k));
        entryDO.setModifier(modifyDictBO.getOperator());
        if (modifyDictBO.getVersion()!=null)
            entryDO.setVersion(modifyDictBO.getVersion());
        return dictEntryRepository.save(entryDO);
    }


}
