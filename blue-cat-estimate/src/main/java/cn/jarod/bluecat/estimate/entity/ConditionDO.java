package cn.jarod.bluecat.estimate.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jarod.jin 2019/11/22
 */
@Setter
@Getter
@ToString
public class ConditionDO implements Serializable {

    private static final long serialVersionUID = -2606978069314630172L;

    /**积分项编码或者对应值*/
    private String conditionKey;

    /**积分项内容*/
    private String conditionText;

    /**单项分值*/
    private BigDecimal perUnitScore;

    /**上限分值*/
    private BigDecimal upperLimitScore;

    /**JS判定*/
    private String decideRegex;


    public ConditionDO(){}

    public ConditionDO(String conditionKey, String conditionText, BigDecimal perUnitScore, BigDecimal upperLimitScore,  String decideRegex){
        this.conditionKey = conditionKey;
        this.conditionText = conditionText;
        this.upperLimitScore = upperLimitScore;
        this.perUnitScore = perUnitScore;
        this.decideRegex = decideRegex;
    }
}
