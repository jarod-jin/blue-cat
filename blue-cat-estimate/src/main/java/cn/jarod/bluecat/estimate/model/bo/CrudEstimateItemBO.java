package cn.jarod.bluecat.estimate.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import cn.jarod.bluecat.estimate.entity.AnswerDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/11/27
 */
@Getter
@Setter
@ToString
public class CrudEstimateItemBO extends BaseModel {

    /**合约编号*/
    private String serialNo;

    /**评估Id*/
    private Long estimateSheetId;

    /**条目序号*/
    private Integer itemNo;

    /**得分*/
    private BigDecimal itemScore;

    /**回答列*/
    private List<AnswerDO> answerJson;

    /**关联系统编号*/
    private String sysCode;
}
