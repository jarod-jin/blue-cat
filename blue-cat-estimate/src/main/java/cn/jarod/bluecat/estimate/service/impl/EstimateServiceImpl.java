package cn.jarod.bluecat.estimate.service.impl;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.estimate.entity.EstimateItemDO;
import cn.jarod.bluecat.estimate.entity.EstimateSheetDO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;
import cn.jarod.bluecat.estimate.repository.EstimateItemRepository;
import cn.jarod.bluecat.estimate.repository.EstimateSheetRepository;
import cn.jarod.bluecat.estimate.service.EstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/11/25
 */
@Slf4j
@Service
public class EstimateServiceImpl implements EstimateService {
    
    private final EstimateSheetRepository estimateSheetRepository;

    private final EstimateItemRepository estimateItemRepository;

    public EstimateServiceImpl(EstimateSheetRepository estimateSheetRepository, EstimateItemRepository estimateItemRepository) {
        this.estimateSheetRepository = estimateSheetRepository;
        this.estimateItemRepository = estimateItemRepository;
    }


    /**
     *
     * @param sheetBO
     * @return
     */
    @Override
    public EstimateSheetDO saveEstimateSheet(CrudEstimateSheetBO sheetBO) {
        EstimateSheetDO estimateSheetDO =  estimateSheetRepository.findBySerialNoAndSysCodeAndUsernameAndFinishMark(sheetBO.getSerialNo(),sheetBO.getSysCode(),sheetBO.getUsername(), Const.NOT_DEL)
                .orElse(new EstimateSheetDO());
        sheetBO.reset();
        BeanHelperUtil.copyNotNullProperties(sheetBO,estimateSheetDO);
        estimateSheetDO.setCreator(sheetBO.getModifier());
        return estimateSheetRepository.save(estimateSheetDO);
    }

    /**
     *
     * @param itemBOList
     * @return
     */
    @Override
    public List<EstimateItemDO> saveEstimateItemList(List<CrudEstimateItemBO> itemBOList) {
        Map<Long, EstimateItemDO> mapSourceDO = estimateItemRepository.findAllById(itemBOList.stream().filter(e->e.getId()!=null).map(CrudEstimateItemBO::getId).collect(Collectors.toList()))
                .stream().collect(Collectors.toConcurrentMap(EstimateItemDO::getId, Function.identity()));
        List<EstimateItemDO>  saveList=  itemBOList.stream().map(i->{
            EstimateItemDO tmp;
            if ((!i.isNew())&& mapSourceDO.containsKey(i.getId())) {
                tmp = mapSourceDO.get(i.getId());
            }else{
                tmp = new EstimateItemDO();
                i.reset();
            }
            BeanHelperUtil.copyNotNullProperties(i,tmp);
            tmp.setCreator(i.getModifier());
            return tmp;
        }).collect(Collectors.toList());
        return estimateItemRepository.saveAll(saveList);
    }

    /**
     *
     * @param queryBO
     * @return
     */
    @Override
    public CrudEstimateSheetBO findEstimate(CrudEstimateSheetBO queryBO) {
        EstimateSheetDO sheetDO = estimateSheetRepository.findBySerialNoAndSysCodeAndUsernameAndFinishMark(queryBO.getSerialNo(),queryBO.getSysCode(),queryBO.getUsername(), queryBO.getFinishedMark())
                .orElseThrow(()->new BaseException(ReturnCode.Q401));
        CrudEstimateSheetBO estimateSheetBO = BeanHelperUtil.createCopyBean(sheetDO, CrudEstimateSheetBO.class);
        List<CrudEstimateItemBO> itemList = estimateItemRepository.findAllByEstimateSheetIdOrderByItemNoAsc(sheetDO.getId()).stream().map(i-> BeanHelperUtil.createCopyBean(i,CrudEstimateItemBO.class))
                .collect(Collectors.toList());
        estimateSheetBO.setCrudEstimateItemList(itemList);
        return estimateSheetBO;
    }
}
