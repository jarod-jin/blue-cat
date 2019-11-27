package cn.jarod.bluecat.estimate.service;

import cn.jarod.bluecat.estimate.entity.ContractItemDO;
import cn.jarod.bluecat.estimate.entity.ContractSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;

import java.util.List;

/**
 * @auther jarod.jin 2019/11/25
 */
public interface IContractService {

    ContractSheetDO saveSheet(CrudContractSheetBO sheetBO);

    List<ContractItemDO> saveItemList(List<CrudContractItemBO> itemBOList);

    CrudContractSheetBO findContract(CrudContractSheetBO queryBO);
}
