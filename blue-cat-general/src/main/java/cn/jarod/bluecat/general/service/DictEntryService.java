package cn.jarod.bluecat.general.service;

import cn.jarod.bluecat.general.entity.DictEntryDO;
import cn.jarod.bluecat.general.model.bo.UpdateEntryItemBO;
import cn.jarod.bluecat.general.model.bo.CrudDictEntryBO;

/**
 * @author jarod.jin 2019/11/19
 */
public interface DictEntryService {

    DictEntryDO queryByDictCode(String dictCode);

    DictEntryDO saveDict(CrudDictEntryBO dictCode);

    DictEntryDO modifyDictEntry(UpdateEntryItemBO modifyDictBO);

    DictEntryDO delDictEntry(UpdateEntryItemBO modifyDictBO);
}
