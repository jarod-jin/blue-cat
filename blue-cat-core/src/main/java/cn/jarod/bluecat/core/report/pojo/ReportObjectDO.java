package cn.jarod.bluecat.core.report.pojo;
import cn.jarod.bluecat.core.security.pojo.DataShareRuleDO;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportObjectDO {

    /**
     * 主表
     */
    protected TableDO primaryTable;

    /**
     * 列名
     */
    private Set<ColumnDO> columns;

    /**
     * join的表集合
     */
    private Set<JoinTableDO> joinTables;

    /**
     * 权限条件
     */
    private List<DataShareRuleDO> shareRules;

    /**
     * 过滤条件
     */
    private DataShareRuleDO filterRules;

}
