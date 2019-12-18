package cn.jarod.bluecat.estimate.service;

import cn.jarod.bluecat.estimate.entity.ContractItemDO;
import cn.jarod.bluecat.estimate.entity.ContractSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;

import java.util.List;

/**
 * @author jarod.jin 2019/11/25
 */
public interface IContractService {

    ContractSheetDO saveContractSheet(CrudContractSheetBO sheetBO);

    List<ContractItemDO> saveContractItemList(List<CrudContractItemBO> itemBOList);

    CrudContractSheetBO findContract(CrudContractSheetBO queryBO);
}
