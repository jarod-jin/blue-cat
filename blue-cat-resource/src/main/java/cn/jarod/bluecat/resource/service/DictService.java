package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.resource.entity.DictDO;
import cn.jarod.bluecat.resource.model.bo.CrudDictEntryBO;
import cn.jarod.bluecat.resource.model.bo.UpdateEntryItemBO;


/**
 * @author jarod.jin 2019/11/19
 */
public interface DictService {


    DictDO queryByDictCode(String dictCode);

    /**
     * 新建修改字典
     * @param entryBO
     * @return
     */
    @TimeDiff
    DictDO saveDict(CrudDictEntryBO entryBO);

    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    DictDO modifyDictEntry(UpdateEntryItemBO modifyDictBO);

    /**
     * 删除字典条目
     * @param modifyDictBO
     * @return
     */
   DictDO delDictEntry(UpdateEntryItemBO modifyDictBO);
}
