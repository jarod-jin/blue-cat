package cn.jarod.bluecat.resource.service.impl;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.resource.entity.DictDO;
import cn.jarod.bluecat.resource.model.bo.UpdateDictItemBO;
import cn.jarod.bluecat.resource.model.bo.CrudDictBO;
import cn.jarod.bluecat.resource.repository.DictRepository;
import cn.jarod.bluecat.resource.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public DictDO findByCategory(String category,String belongTo) {
        return dictRepository.findOneByCategoryAndBelongTo(category, belongTo).orElseThrow(ApiResultUtil::fail4NotFound);
    }

    /**
     * 创建字段
     * @param entryBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDO create(CrudDictBO entryBO) {
        if (dictRepository.findOneByCategoryAndBelongTo(entryBO.getCategory(),entryBO.getBelongTo()).isPresent()) {
            throw ApiResultUtil.fail4Existed();
        }
        DictDO entryDO = BeanHelperUtil.createCopyBean(entryBO, DictDO.class);
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
    @Transactional(rollbackFor = Exception.class)
    public DictDO update(CrudDictBO entryBO) {
        DictDO entryDO = findByCategory(entryBO.getCategory(), entryBO.getBelongTo());
        BeanHelperUtil.copyNotNullProperties(entryBO, entryDO);
        entryDO.setGmtModified(LocalDateTime.now());
        return dictRepository.save(entryDO);
    }

    /**
     * 删除字典
     * @param entryBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delDict(CrudDictBO entryBO) {
        DictDO entryDO = findByCategory(entryBO.getCategory(),entryBO.getBelongTo());
        entryDO.setVersion(entryBO.getVersion());
        dictRepository.delete(entryDO);
    }

    /**
     * 增加字典条目
     * @param updateBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDO addItems(UpdateDictItemBO updateBO) {
        DictDO entryDO = findByCategory(updateBO.getCategory(),updateBO.getBelongTo());
        entryDO.getItems().putAll(updateBO.getItems());
        entryDO.setModifier(updateBO.getModifier());
        if (updateBO.getVersion() != null) {
            entryDO.setVersion(updateBO.getVersion());
        }
        return dictRepository.save(entryDO);
    }

    /**
     * 删除字典条目
     * @param removeBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDO removeItems(UpdateDictItemBO removeBO) {
        DictDO entryDO = findByCategory(removeBO.getCategory(),removeBO.getBelongTo());
        removeBO.getItems().forEach((k, v) -> entryDO.getItems().remove(k));
        entryDO.setModifier(removeBO.getModifier());
        if (removeBO.getVersion() != null) {
            entryDO.setVersion(removeBO.getVersion());
        }
        return dictRepository.save(entryDO);
    }

}
