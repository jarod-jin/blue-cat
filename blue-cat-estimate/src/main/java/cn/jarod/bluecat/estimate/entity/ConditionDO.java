package cn.jarod.bluecat.estimate.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @auther jarod.jin 2019/11/22
 */
@Setter
@Getter
public class ConditionDO implements Serializable {

    private static final long serialVersionUID = -2606978069314630172L;

    //积分项编码或者对应值
    private String conditionKey;

    //积分项内容
    private String conditionText;

    //分数
    private BigDecimal score;

    //临界值
    private BigDecimal minValue;

    //最大值
    private BigDecimal maxValue;

    //判定正则
    private String decideRegex;


    public ConditionDO(){}

    public ConditionDO(String conditionKey, String conditionText, BigDecimal score, BigDecimal minValue, BigDecimal maxValue, String decideRegex){
        this.conditionKey = conditionKey;
        this.conditionText = conditionText;
        this.score = score;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.decideRegex = decideRegex;
    }
}
