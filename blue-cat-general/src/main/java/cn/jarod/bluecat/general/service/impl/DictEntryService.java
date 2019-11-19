package cn.jarod.bluecat.general.service.impl;

import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.general.entity.DictEntryDO;
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
        return null;
    }

    @Override
    @Transactional
    public DictEntryDO saveDictCode(SaveDictEntryBO entryBO) {
        DictEntryDO entryDO = dictEntryRepository.findByDictCode(entryBO.getDictCode()).orElse(new DictEntryDO());
        BeanHelperUtil.copyNotNullProperties(entryBO,entryDO);
        entryDO.setModifier(entryBO.getOperator());
        entryDO.setCreator(entryBO.getOperator());
        return dictEntryRepository.save(entryDO);
    }


}
