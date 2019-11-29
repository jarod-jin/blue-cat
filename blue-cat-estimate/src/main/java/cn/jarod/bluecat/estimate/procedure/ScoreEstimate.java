package cn.jarod.bluecat.estimate.procedure;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.estimate.entity.AnswerDO;
import cn.jarod.bluecat.estimate.entity.ConditionDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;
import cn.jarod.bluecat.estimate.service.IContractService;
import cn.jarod.bluecat.estimate.service.IEstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @auther jarod.jin 2019/11/28
 */
@Slf4j
@Service
public class ScoreEstimate {

    private static final String JS = "js";

    private static final String A = "a";

    @Autowired
    private IContractService contractService;

    @Autowired
    private IEstimateService estimateService;

    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public void countScoreByEstimateSheet(CrudEstimateSheetBO estimateSheet){
        CrudContractSheetBO contract = contractService.findContract(new CrudContractSheetBO(estimateSheet.getSerialNo(),estimateSheet.getSysCode()));
        CrudEstimateSheetBO estimate = estimateService.findEstimate(new CrudEstimateSheetBO(estimateSheet.getSerialNo(),estimateSheet.getUsername(),estimateSheet.getSysCode(), Const.NOT_DEL));
        List<CrudEstimateItemBO> crudEstimateItemList = estimate.getCrudEstimateItemList().stream().peek(e->{
            CrudContractItemBO contractItem =  contract.getContractItemBOList().get(e.getItemNo());
            Map<String, ConditionDO> conditionMap = contractItem.getConditionJson().stream().collect(Collectors.toMap(ConditionDO::getConditionKey, Function.identity()));
            double tmpScore = e.getAnswerJson().stream().mapToDouble(
                    a->{
                        ConditionDO standard = conditionMap.get(a.getConditionKey());
                        BigDecimal deltaScore = countScoreByAnswer(standard , a);
                        return deltaScore.doubleValue();
                    }
            ).sum();
            e.setItemScore(BigDecimal.valueOf(tmpScore));
        }).collect(Collectors.toList());
        estimate.setCrudEstimateItemList(crudEstimateItemList);
        estimate.setTotalScore(BigDecimal.valueOf(crudEstimateItemList.stream().mapToDouble(e->e.getItemScore().doubleValue()).sum()));
        estimateService.saveEstimateSheet(estimate);
    }

    private BigDecimal countScoreByAnswer(ConditionDO standard , AnswerDO answer){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName(JS);
        engine.put(A, answer.getAnswerText().trim());
        try {
            if  (String.valueOf(engine.eval(standard.getDecideRegex())).equals("true")){
                if (standard.getPerUnitScore().compareTo(new BigDecimal(0))>0){
                    BigDecimal tmp = standard.getPerUnitScore().multiply(new BigDecimal(answer.getAnswerText().trim()));
                    return tmp.compareTo(standard.getUpperLimitScore())>0 ? standard.getUpperLimitScore():tmp;
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
