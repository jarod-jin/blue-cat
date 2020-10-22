package cn.jarod.bluecat.core.report.pojo;


import cn.jarod.bluecat.core.api.pojo.MysqlDO;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author wb_zw16
 * @date 2020/8/5 16:33
 */
@Data
public class ReportItemDO extends MysqlDO implements Serializable {

    private String filterLogic;

    private List<ConditionConfigDO> conditions;
}
