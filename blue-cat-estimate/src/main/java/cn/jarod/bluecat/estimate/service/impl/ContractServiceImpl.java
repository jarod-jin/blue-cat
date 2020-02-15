package cn.jarod.bluecat.estimate.service.impl;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.estimate.entity.ContractItemDO;
import cn.jarod.bluecat.estimate.entity.ContractSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;
import cn.jarod.bluecat.estimate.repository.ContractItemRepository;
import cn.jarod.bluecat.estimate.repository.ContractSheetRepository;
import cn.jarod.bluecat.estimate.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 由于Item不会单独存在，故整体作为一个Service
 * @author jarod.jin 2019/11/25
 */
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractItemRepository contractItemRepository;

    private final ContractSheetRepository contractSheetRepository;

    @Autowired
    public ContractServiceImpl(ContractSheetRepository contractSheetRepository, ContractItemRepository contractItemRepository) {
        this.contractSheetRepository = contractSheetRepository;
        this.contractItemRepository = contractItemRepository;
    }

    /**
     * 保存合约
     * @param sheetBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractSheetDO saveContractSheet(CrudContractSheetBO sheetBO) {
        ContractSheetDO contractSheetDO =  contractSheetRepository.findBySerialNoAndBelongTo(sheetBO.getSerialNo(),sheetBO.getBelongTo()).orElse(new ContractSheetDO());
        sheetBO.reset();
        BeanHelperUtil.copyNotNullProperties(sheetBO,contractSheetDO);
        contractSheetDO.setCreator(sheetBO.getModifier());
        return contractSheetRepository.save(contractSheetDO);
    }

    /**
     * 保存题目列表，可以分阶段提交用id做判定
     * @param itemBOList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ContractItemDO> saveContractItemList(List<CrudContractItemBO> itemBOList) {
        Map<Long, ContractItemDO> mapSourceDO = contractItemRepository.findAllById(itemBOList.stream().filter(e->e.getId()!=null).map(CrudContractItemBO::getId).collect(Collectors.toList()))
                .stream().collect(Collectors.toConcurrentMap(ContractItemDO::getId, Function.identity()));
        List<ContractItemDO>  saveList=  itemBOList.stream().map(i->{
            ContractItemDO tmp;
            if ((!i.isNew())&& mapSourceDO.containsKey(i.getId())) {
                tmp = mapSourceDO.get(i.getId());
            }else{
                tmp = new ContractItemDO();
                i.reset();
            }
            BeanHelperUtil.copyNotNullProperties(i,tmp);
            tmp.setCreator(i.getModifier());
            return tmp;
        }).collect(Collectors.toList());
        return contractItemRepository.saveAll(saveList);
    }

    /**
     * 查询合约/问卷以及条目
     * @param queryBO
     * @return
     */
    @Override
    @TimeDiff
    public CrudContractSheetBO findContract(@Valid CrudContractSheetBO queryBO) {
        ContractSheetDO sheetDO = contractSheetRepository.findBySerialNoAndBelongTo(queryBO.getSerialNo(),queryBO.getBelongTo()).orElseThrow(()->new BaseException(ReturnCode.NOT_FOUND));
        CrudContractSheetBO contractSheetBO = BeanHelperUtil.createCopyBean(sheetDO, CrudContractSheetBO.class);
        List<CrudContractItemBO> itemList = contractItemRepository.findAllBySerialNoAndBelongToOrderByItemNoAsc(queryBO.getSerialNo(),queryBO.getBelongTo())
                .stream().map(i->BeanHelperUtil.createCopyBean(i,CrudContractItemBO.class)).collect(Collectors.toList());
        contractSheetBO.setContractItemBOList(itemList);
        return contractSheetBO;
    }
}
