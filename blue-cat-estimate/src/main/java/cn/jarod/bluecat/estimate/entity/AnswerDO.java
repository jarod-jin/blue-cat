package cn.jarod.bluecat.estimate.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @auther jarod.jin 2019/11/25
 */
@Setter
@Getter
public class AnswerDO implements Serializable {

    private static final long serialVersionUID = 4973963601272522066L;
    //积分项编码或者对应值
    private String conditionKey;

    //分数
    private BigDecimal score;

    //回答
    private String answerText;

    public AnswerDO(){}


    public AnswerDO(String conditionKey, BigDecimal score, String answerText){
        this.conditionKey = conditionKey;
        this.score = score;
        this.answerText = answerText;
    }
}
