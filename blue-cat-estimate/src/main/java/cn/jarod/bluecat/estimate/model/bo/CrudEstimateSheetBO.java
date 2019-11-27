package cn.jarod.bluecat.estimate.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @auther jarod.jin 2019/11/27
 */
@Getter
@Setter
public class CrudEstimateSheetBO extends BaseModel {

    //合约编号
    private String serialNo;

    //评估编号
    private String estimateNo;

    //用户唯一标识
    private String username;

    //用户名
    private String nickname;

    //总得分
    private BigDecimal totalScore;

    //关联系统编号
    private String sysCode;
}
