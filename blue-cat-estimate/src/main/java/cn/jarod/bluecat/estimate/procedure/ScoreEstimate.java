package cn.jarod.bluecat.estimate.procedure;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.common.EntityType;
import cn.jarod.bluecat.estimate.entity.AnswerDO;
import cn.jarod.bluecat.estimate.entity.ConditionDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;
import cn.jarod.bluecat.estimate.service.ContractService;
import cn.jarod.bluecat.estimate.service.EstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/11/28
 */
@Slf4j
@Service
public class ScoreEstimate {

    private static final String JS = "js";

    private static final String A = "a";

    private final ContractService contractService;

    private final EstimateService estimateService;

    public ScoreEstimate(ContractService contractService, EstimateService estimateService) {
        this.contractService = contractService;
        this.estimateService = estimateService;
    }

    /**
     * 根据回答计算得分
     * @param estimateSheet
     */
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public void countScoreByEstimateSheet(CrudEstimateSheetBO estimateSheet){
        CrudContractSheetBO contract = contractService.findContract(new CrudContractSheetBO(estimateSheet.getSerialNo(),estimateSheet.getBelongTo()));
        Map<Integer, CrudContractItemBO> itemMap = contract.getContractItemBOList().stream().collect(Collectors.toMap(CrudContractItemBO::getItemNo,Function.identity()));
        CrudEstimateSheetBO estimate = estimateService.findEstimate(new CrudEstimateSheetBO(estimateSheet.getSerialNo(),estimateSheet.getUsername(),estimateSheet.getBelongTo(), EntityType.DEL.getType()));
        List<CrudEstimateItemBO> crudEstimateItemList = estimate.getCrudEstimateItemList().stream().peek(e->{
            CrudContractItemBO contractItem = itemMap.get(e.getItemNo());
            Map<String, ConditionDO> conditionMap = contractItem.getConditionJson().stream().collect(Collectors.toMap(ConditionDO::getConditionKey, Function.identity()));
            double tmpScore = e.getAnswerJson().stream().mapToDouble(
                    a->{
                        ConditionDO standard = conditionMap.get(a.getConditionKey());
                        BigDecimal deltaScore = countScoreByAnswer(standard , a);
                        return deltaScore.doubleValue();
                    }
            ).sum();
            e.setItemScore(tmpScore > contractItem.getItemScore().doubleValue() ? contractItem.getItemScore(): BigDecimal.valueOf(tmpScore));
        }).collect(Collectors.toList());
        estimateService.saveEstimateItemList(crudEstimateItemList);
        estimate.setTotalScore(BigDecimal.valueOf(crudEstimateItemList.stream().mapToDouble(e->e.getItemScore().doubleValue()).sum()));
        estimateService.saveEstimateSheet(estimate);
    }

    /**
     * JS脚本积分标准判断
     * @param standard
     * @param answer
     * @return
     */
    private BigDecimal countScoreByAnswer(ConditionDO standard , AnswerDO answer){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName(JS);
        engine.put(A, answer.getAnswerText().trim());
        try {
            if  (Boolean.parseBoolean(String.valueOf(engine.eval(standard.getDecideRegex())))){
                if (standard.getPerUnitScore().doubleValue() > 0){
                    BigDecimal tmp = standard.getPerUnitScore().multiply(new BigDecimal(answer.getAnswerText().trim()));
                    return tmp.compareTo(standard.getUpperLimitScore()) > 0 ? standard.getUpperLimitScore() : tmp;
                }else{
                    return standard.getUpperLimitScore();
                }
            }
        } catch (ScriptException |NumberFormatException e) {
            log.error(e.getMessage());
        }
        return new BigDecimal(0);
    }
}
