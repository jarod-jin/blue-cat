package cn.jarod.bluecat.estimate.procedure;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.estimate.entity.AnswerDO;
import cn.jarod.bluecat.estimate.entity.ConditionDO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractItemBO;
import cn.jarod.bluecat.estimate.model.bo.CrudContractSheetBO;
import cn.jarod.bluecat.estimate.model.bo.CrudEstimateSheetBO;
import cn.jarod.bluecat.estimate.service.IContractService;
import cn.jarod.bluecat.estimate.service.IEstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2019/11/28
 */
@Slf4j
@Service
public class ScoreEstimate {

    private static final String NOT_EQUALS = "!=";

    private static final String EQUALS = "==";

    private static final String OVER = ">";

    private static final String LESS = "<";
    private static final String JS = "js";
    private static final String A = "a";

    @Autowired
    private IContractService contractService;

    @Autowired
    private IEstimateService estimateService;

    public void countScoreByEstimateSheet(CrudEstimateSheetBO estimateSheet){
        CrudContractSheetBO contract = contractService.findContract(new CrudContractSheetBO(estimateSheet.getSerialNo(),estimateSheet.getSysCode()));
        CrudEstimateSheetBO estimate = estimateService.findEstimate(new CrudEstimateSheetBO(estimateSheet.getSerialNo(),estimateSheet.getUsername(),estimateSheet.getSysCode(), Const.NOT_DEL));
        estimate.getCrudEstimateItemList().forEach(e->{
            CrudContractItemBO contractItem =  contract.getContractItemBOList().get(e.getItemNo());
            BigDecimal tmpScore = new BigDecimal(0);
            Map<String, ConditionDO> conditionMap = contractItem.getConditionJson().stream().collect(Collectors.toMap(ConditionDO::getConditionKey, Function.identity()));
            e.getAnswerJson().forEach(
                    a->{
                        ConditionDO judgeDO = conditionMap.get(a.getConditionKey());
                    }
            );

        });
    }

    private BigDecimal countScoreByAnswer(ConditionDO judge , AnswerDO answer){
        boolean sameTimeSatisfy = false;
        if (judge.getDecideRegex().contains("&&")){
            if (judge.getDecideRegex().contains("||"))
                throw new BaseException(ReturnCode.R501);
            else
                sameTimeSatisfy = true;
        }
        String[] strArr = judge.getDecideRegex().split("[&]{2}|[|]{2}");

        return null;
    }

    private Object satisfyForStandard(String standardStr,String answerStr) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName(JS);
        engine.put(A, answerStr.trim());
        return engine.eval(standardStr);
    }

}
