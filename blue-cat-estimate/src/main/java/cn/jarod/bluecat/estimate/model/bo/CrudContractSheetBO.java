package cn.jarod.bluecat.estimate.model.bo;

import cn.jarod.bluecat.core.model.MysqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author jarod.jin 2019/11/26
 */
@Getter
@Setter
@ToString
public class CrudContractSheetBO extends MysqlModel {

    /**合约编号*/
    @NotBlank
    private String serialNo;

    /**客户编号*/
    private String customerNo;

    /**合约名称*/
    private String contractName;

    /**合约内容*/
    private String contractText;

    /**关联系统编号*/
    @NotBlank
    private String belongTo;

    /**合同条目*/
    List<CrudContractItemBO> contractItemBOList;

    public CrudContractSheetBO(){

    }

    public CrudContractSheetBO(String serialNo, String belongTo){
        this.serialNo = serialNo;
        this.belongTo = belongTo;
    }

}
