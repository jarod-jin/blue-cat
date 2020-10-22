package cn.jarod.bluecat.core.report.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wb_wxuser61
 * @data 2020/7/17 16:41
 */
@Data
public class ConditionConfigDTO implements Serializable {

    //字段名
    private String fieldname;

    //字段值
    private String fieldValue;

    //条件 > < =
    private String operatorName;

}
