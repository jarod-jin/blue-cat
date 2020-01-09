package cn.jarod.bluecat.resource.service;

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
     * @param belong
     * @return
     */
    DictDO findByCategory(String category,String belong);


    /**
     * 修改字典
     * @param entryBO
     * @return
     *
     */
    DictDO create(CrudDictBO entryBO);

    /**
     * 修改字典
     * @param entryBO
     * @return
     *
     */
    DictDO update(CrudDictBO entryBO);

    /**
     * 删除字典
     * @param entryBO
     * @return
     *
     */
    void delDict(CrudDictBO entryBO);


    /**
     * 修改字典条目
     * @param modifyDictBO
     * @return
     */
    DictDO addItems(UpdateDictItemBO modifyDictBO);

    /**
     * 删除字典条目
     * @param modifyDictBO
     * @return
     */
   DictDO removeItems(UpdateDictItemBO modifyDictBO);
}
