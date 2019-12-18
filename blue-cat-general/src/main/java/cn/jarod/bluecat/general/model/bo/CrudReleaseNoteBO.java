package cn.jarod.bluecat.general.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/11/20
 */
@Getter
@Setter
public class CrudReleaseNoteBO extends BaseModel {

    //版本号
    private String releaseVersion;

    //字典键-值
    private List<String> releaseNote;

    //打包版本序号号
    private BigDecimal buildNo;

    //描述
    private String terminalType;

    //关联系统编号
    private String sysCode;

}
