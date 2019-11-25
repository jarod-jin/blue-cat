package cn.jarod.bluecat.estimate.service.impl;

import cn.jarod.bluecat.estimate.repository.ContractItemRepository;
import cn.jarod.bluecat.estimate.repository.ContractSheetRepository;
import cn.jarod.bluecat.estimate.service.IContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther jarod.jin 2019/11/25
 */
@Slf4j
@Service
public class ContractService implements IContractService {

    private final ContractItemRepository contractItemRepository;

    private final ContractSheetRepository contractSheetRepository;

    @Autowired
    public ContractService(ContractSheetRepository contractSheetRepository, ContractItemRepository contractItemRepository) {
        this.contractSheetRepository = contractSheetRepository;
        this.contractItemRepository = contractItemRepository;
    }
}
