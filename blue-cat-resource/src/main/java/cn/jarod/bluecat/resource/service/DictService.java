package cn.jarod.bluecat.resource.service;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.resource.entity.DictDO;
import cn.jarod.bluecat.resource.model.bo.CrudDictBO;
import cn.jarod.bluecat.resource.model.bo.UpdateDictItemBO;



/**
 * @author jarod.jin 2019/11/19
 */
public interface DictService {


    /**
     * 查询字典数据
     * @param category
     * @return
     */
    DictDO findByCategory(String category);


    /**
     * 修改字典
     * @param entryBO
     * @return
     *
     */
    @TimeDiff
    DictDO create(CrudDictBO entryBO);

    /**
     * 修改字典
     * @param entryBO
     * @return
     *
     */
    @TimeDiff
    DictDO update(CrudDictBO entryBO);

    /**
     * 删除字典
     * @param entryBO
     * @return
     *
     */
    @TimeDiff
    void delDict(CrudDictBO entryBO);


    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    DictDO updateItems(UpdateDictItemBO modifyDictBO);

    /**
     * 删除字典条目
     * @param modifyDictBO
     * @return
     */
   DictDO delItem(UpdateDictItemBO modifyDictBO);
}
