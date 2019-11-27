package cn.jarod.bluecat.estimate.service.impl;

import cn.jarod.bluecat.estimate.repository.EstimateItemRepository;
import cn.jarod.bluecat.estimate.repository.EstimateSheetRepository;
import cn.jarod.bluecat.estimate.service.IEstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @auther jarod.jin 2019/11/25
 */
@Slf4j
@Service
public class EstimateService implements IEstimateService {
    
    private final EstimateSheetRepository estimateSheetRepository;

    private final EstimateItemRepository estimateItemRepository;

    public EstimateService(EstimateSheetRepository estimateSheetRepository, EstimateItemRepository estimateItemRepository) {
        this.estimateSheetRepository = estimateSheetRepository;
        this.estimateItemRepository = estimateItemRepository;
    }



}
