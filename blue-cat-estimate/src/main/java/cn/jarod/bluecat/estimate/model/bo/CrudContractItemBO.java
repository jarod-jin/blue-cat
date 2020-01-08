package cn.jarod.bluecat.estimate.model.bo;

import cn.jarod.bluecat.core.model.RdsModel;
import cn.jarod.bluecat.estimate.entity.ConditionDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/11/26
 */
@Getter
@Setter
@ToString
public class CrudContractItemBO extends RdsModel {

    /**合约编号*/
    private String serialNo;

    /**条目序号*/
    private Integer itemNo;

    /**内容*/
    private String itemText;

    /**得分*/
    private BigDecimal itemScore;

    /**选项头 0-数字 1-字母*/
    private Integer conditionMark;

    /**关联系统编号*/
    private String belongTo;

    /**选项列*/
    private List<ConditionDO> conditionJson;

}
