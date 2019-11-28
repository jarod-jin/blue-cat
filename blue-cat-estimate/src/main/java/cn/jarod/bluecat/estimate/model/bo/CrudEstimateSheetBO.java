package cn.jarod.bluecat.estimate.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * @auther jarod.jin 2019/11/27
 */
@Getter
@Setter
public class CrudEstimateSheetBO extends BaseModel {

    //合约编号
    @NotBlank
    private String serialNo;

    //用户唯一标识
    @NotBlank
    private String username;

    //用户名
    private String nickname;

    //总得分
    private BigDecimal totalScore;

    //关联系统编号
    @NotBlank
    private String sysCode;

    //完成标志0 未完成 1 已完成
    private Integer finishedMark;

    //
    private List<CrudEstimateItemBO> crudEstimateItemList;


    public CrudEstimateSheetBO(){}

    public CrudEstimateSheetBO(String serialNo, String username, String sysCode, Integer finishedMark){
        this.serialNo = serialNo;
        this.username = username;
        this.sysCode = sysCode;
        this.finishedMark = finishedMark;
    }
}
