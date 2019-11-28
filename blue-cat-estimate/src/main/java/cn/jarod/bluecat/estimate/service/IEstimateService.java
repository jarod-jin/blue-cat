package cn.jarod.bluecat.estimate.service;

import cn.jarod.bluecat.estimate.entity.EstimateItemDO;
import cn.jarod.bluecat.estimate.entity.EstimateSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/25
 */
public interface IEstimateService {

    EstimateSheetDO saveEstimateSheet(CrudEstimateSheetBO sheetBO);

    List<EstimateItemDO> saveEstimateItemList(List<CrudEstimateItemBO> itemBOList);

    CrudEstimateSheetBO findEstimate(CrudEstimateSheetBO queryBO);

}
