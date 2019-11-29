package cn.jarod.bluecat.estimate.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @auther jarod.jin 2019/11/26
 */
@Getter
@Setter
public class CrudContractSheetBO extends BaseModel {

    //合约编号
    @NotBlank
    private String serialNo;

    //客户编号
    private String customerNo;

    //合约名称
    private String contractName;

    //合约内容
    private String contractText;

    //关联系统编号
    @NotBlank
    private String sysCode;

    //合同条目
    List<CrudContractItemBO> contractItemBOList;

    public CrudContractSheetBO(){

    }

    public CrudContractSheetBO(String serialNo, String sysCode){
        this.serialNo = serialNo;
        this.sysCode = sysCode;
    }

}