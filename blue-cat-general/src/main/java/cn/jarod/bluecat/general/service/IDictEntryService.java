package cn.jarod.bluecat.general.service;

import cn.jarod.bluecat.general.entity.DictEntryDO;
import cn.jarod.bluecat.general.model.bo.UpdateDictEntryBO;
import cn.jarod.bluecat.general.model.bo.SaveDictEntryBO;

/**
 * @auther jarod.jin 2019/11/19
 */
public interface IDictEntryService {

    DictEntryDO queryByDictCode(String dictCode);

    DictEntryDO saveDict(SaveDictEntryBO dictCode);

    DictEntryDO modifyDictEntry(UpdateDictEntryBO modifyDictBO);

    DictEntryDO delDictEntry(UpdateDictEntryBO modifyDictBO);
}
