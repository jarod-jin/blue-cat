package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.resource.entity.DictDO;
import cn.jarod.bluecat.resource.model.bo.UpdateEntryItemBO;
import cn.jarod.bluecat.resource.model.bo.CrudDictEntryBO;
import cn.jarod.bluecat.resource.repository.DictRepository;
import cn.jarod.bluecat.resource.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jarod.jin 2019/11/19
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {
    
    private final DictRepository dictRepository;

    @Autowired
    public DictServiceImpl( DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    @Override
    public DictDO queryByDictCode(String dictCode) {
//        return dictRepository.findByDictCode(dictCode).orElseThrow(()->new BaseException(ReturnCode.NOT_FOUND));
        return null;
    }

    /**
     * 新建修改字典
     * @param entryBO
     * @return
     */
    @Override
    @TimeDiff
    public DictDO saveDict(CrudDictEntryBO entryBO) {
//        DictDO entryDO = dictRepository.findByDictCode(entryBO.getDictCode()).orElse(new DictDO());
//        BeanHelperUtil.copyNotNullProperties(entryBO,entryDO);
//        entryDO.setCreator(entryBO.getModifier());
//        return dictRepository.save(entryDO);
        return null;
    }

    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TimeDiff
    public DictDO modifyDictEntry(UpdateEntryItemBO modifyDictBO) {
//        DictDO entryDO = dictRepository.findByDictCode(modifyDictBO.getDictCode()).orElseThrow(()->new BaseException(ReturnCode.NOT_ACCEPTABLE));
//        entryDO.getEntryJson().putAll(modifyDictBO.getEntryJson());
//        entryDO.setModifier(modifyDictBO.getModifier());
//        if (modifyDictBO.getVersion()!=null) {
//            entryDO.setVersion(modifyDictBO.getVersion());
//        }
//        return dictRepository.save(entryDO);
        return null;
    }

    /**
     * 删除字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TimeDiff
    public DictDO delDictEntry(UpdateEntryItemBO modifyDictBO) {
//        DictDO entryDO = dictRepository.findByDictCode(modifyDictBO.getDictCode()).orElseThrow(()->new BaseException(ReturnCode.GONE));
//        modifyDictBO.getEntryJson().forEach((k,v)-> entryDO.getEntryJson().remove(k));
//        entryDO.setModifier(modifyDictBO.getModifier());
//        if (modifyDictBO.getVersion()!=null) {
//            entryDO.setVersion(modifyDictBO.getVersion());
//        }
//        return dictRepository.save(entryDO);
        return null;
    }


}
