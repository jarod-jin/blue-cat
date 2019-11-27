package cn.jarod.bluecat.estimate.service;

import cn.jarod.bluecat.estimate.entity.EstimateItemDO;
import cn.jarod.bluecat.estimate.entity.EstimateSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/25
 */
public interface IEstimateService {


    EstimateSheetDO saveSheet(CrudEstimateSheetBO sheetBO);

    List<EstimateItemDO> saveItemList(List<CrudEstimateItemBO> itemBOList);

    CrudEstimateSheetBO findContract(CrudEstimateSheetBO queryBO);
}
