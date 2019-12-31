package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.DictDO;
import cn.jarod.bluecat.resource.model.bo.UpdateDictItemBO;
import cn.jarod.bluecat.resource.model.bo.CrudDictBO;
import cn.jarod.bluecat.resource.repository.DictRepository;
import cn.jarod.bluecat.resource.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author jarod.jin 2019/11/19
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {

    private final DictRepository dictRepository;

    @Autowired
    public DictServiceImpl(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    /**
     * 通过字典项类目查询字典项
     * @param category 字典类目
     * @return DictDO
     */
    @Override
    @TimeDiff
    public DictDO findByCategory(String category) {
        return dictRepository.findOneByCategory(category).orElseThrow(()->new BaseException(ReturnCode.NOT_FOUND));
    }

    /**
     * 创建字段
     * @param entryBO
     * @return
     */
    @Override
    public DictDO create(CrudDictBO entryBO) {
        if (dictRepository.findOneByCategory(entryBO.getCategory()).isPresent()){
            throw new BaseException(ReturnCode.INVALID_REQUEST);
        }
        DictDO entryDO =  BeanHelperUtil.createCopyBean(entryBO,DictDO.class);
        entryDO.setGmtCreate(LocalDateTime.now());
        entryDO.setGmtModified(LocalDateTime.now());
        entryDO.setCreator(entryBO.getModifier());
        entryDO.setVersion(0);
        return dictRepository.insert(entryDO);
    }

    /**
     * 新建修改字典
     * @param entryBO
     * @return
     */
    @Override
    @TimeDiff
    public DictDO update(CrudDictBO entryBO) {
        DictDO entryDO = dictRepository.findOneByCategory(entryBO.getCategory()).orElseThrow(()->new BaseException(ReturnCode.NOT_FOUND));
        BeanHelperUtil.copyNotNullProperties(entryBO,entryDO);
        entryDO.setGmtModified(LocalDateTime.now());
        return dictRepository.save(entryDO);
    }

    /**
     * 删除字典
     * @param entryBO
     * @return
     */
    @Override
    public void delDict(CrudDictBO entryBO) {
        DictDO entryDO =  dictRepository.findOneByCategory(entryBO.getCategory()).orElseThrow(()->new BaseException(ReturnCode.NOT_FOUND));
        entryDO.setVersion(entryBO.getVersion());
        dictRepository.delete(entryDO);
    }

    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TimeDiff
    public DictDO updateItems(UpdateDictItemBO modifyDictBO) {
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
    public DictDO delItem(UpdateDictItemBO modifyDictBO) {
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
