package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.DictEntryDO;
import cn.jarod.bluecat.resource.model.bo.UpdateEntryItemBO;
import cn.jarod.bluecat.resource.model.bo.CrudDictEntryBO;
import cn.jarod.bluecat.resource.repository.DictEntryRepository;
import cn.jarod.bluecat.resource.service.DictEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jarod.jin 2019/11/19
 */
@Slf4j
@Service
public class DictEntryServiceImpl implements DictEntryService {

    private final DictEntryRepository dictEntryRepository;

    @Autowired
    public DictEntryServiceImpl(DictEntryRepository dictEntryRepository) {
        this.dictEntryRepository = dictEntryRepository;
    }

    @Override
    public DictEntryDO queryByDictCode(String dictCode) {
        return dictEntryRepository.findByDictCode(dictCode).orElseThrow(()->new BaseException(ReturnCode.NOT_FOUND));
    }

    /**
     * 新建修改字典
     * @param entryBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TimeDiff
    public DictEntryDO saveDict(CrudDictEntryBO entryBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(entryBO.getDictCode()).orElse(new DictEntryDO());
        BeanHelperUtil.copyNotNullProperties(entryBO,entryDO);
        entryDO.setCreator(entryBO.getModifier());
        return dictEntryRepository.save(entryDO);
    }

    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TimeDiff
    public DictEntryDO modifyDictEntry(UpdateEntryItemBO modifyDictBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(modifyDictBO.getDictCode()).orElseThrow(()->new BaseException(ReturnCode.NOT_ACCEPTABLE));
        entryDO.getEntryJson().putAll(modifyDictBO.getEntryJson());
        entryDO.setModifier(modifyDictBO.getModifier());
        if (modifyDictBO.getVersion()!=null) {
            entryDO.setVersion(modifyDictBO.getVersion());
        }
        return dictEntryRepository.save(entryDO);
    }

    /**
     * 删除字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TimeDiff
    public DictEntryDO delDictEntry(UpdateEntryItemBO modifyDictBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(modifyDictBO.getDictCode()).orElseThrow(()->new BaseException(ReturnCode.GONE));
        modifyDictBO.getEntryJson().forEach((k,v)-> entryDO.getEntryJson().remove(k));
        entryDO.setModifier(modifyDictBO.getModifier());
        if (modifyDictBO.getVersion()!=null) {
            entryDO.setVersion(modifyDictBO.getVersion());
        }
        return dictEntryRepository.save(entryDO);
    }


}
