package cn.jarod.bluecat.general.model.dto;

import cn.jarod.bluecat.core.model.BaseQO;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/11/20
 */
@Getter
@Setter
public class QueryReleaseDTO extends BaseQO {

    //终端类别
    private String terminalType;

    //关联系统编号
    private String sysCode;
}