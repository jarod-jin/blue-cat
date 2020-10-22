package cn.jarod.bluecat.core.report.pojo;
import cn.jarod.bluecat.core.oauth.pojo.DataShareRules;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportObjectDTO {

    /**
     * 主表
     */
    protected TableDTO primaryTable;

    /**
     * 列名
     */
    private Set<ColumnDTO> columns;

    /**
     * join的表集合
     */
    private Set<JoinTableDTO> joinTables;

    /**
     * 权限条件
     */
    private List<DataShareRules> shareRules;

    /**
     * 过滤条件
     */
    private DataShareRules filterRules;

}
