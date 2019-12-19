package cn.jarod.bluecat.general.service;

import cn.jarod.bluecat.general.entity.DictEntryDO;
import cn.jarod.bluecat.general.model.bo.UpdateEntryItemBO;
import cn.jarod.bluecat.general.model.bo.CrudDictEntryBO;

/**
 * @author jarod.jin 2019/11/19
 */
public interface DictEntryService {

    /**
     * 查询字典
     * @param dictCode 字典
     * @return DictEntryDO
     */
    DictEntryDO queryByDictCode(String dictCode);

    /**
     * 新建保存整个字典
     * @param dictCode 字典
     * @return DictEntryDO
     */
    DictEntryDO saveDict(CrudDictEntryBO dictCode);

    /**
     * 修改单项
     * @param modifyDictBO 字典
     * @return DictEntryDO
     */
    DictEntryDO modifyDictEntry(UpdateEntryItemBO modifyDictBO);

    /**
     * 删除
     * @param modifyDictBO  字典
     * @return DictEntryDO
     */
    DictEntryDO delDictEntry(UpdateEntryItemBO modifyDictBO);
}
