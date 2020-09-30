package cn.jarod.bluecat.report.pojo;

import cn.jarod.bluecat.core.entity.MysqlEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author wb_zw16
 * @date 2020/8/5 16:33
 */
@Data
public class ReportItemDTO extends MysqlEntity implements Serializable {

    private String filterLogic;

    private List<ConditionConfigDTO> conditions;
}
