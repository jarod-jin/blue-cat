package cn.jarod.bluecat.estimate.entity;

import java.math.BigDecimal;

/**
 * @auther jarod.jin 2019/11/22
 */
public class ConditionDO {

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

}
