package cn.jarod.bluecat.core.report.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinTableDTO extends TableDTO{

    /**
     * 主表列名
     */
    private String primaryColumn;

    /**
     * join的表列名
     */
    private String ownColumn;

    /**
     * join的方式 left join 或者 inner join
     */
    private String joinType;


    public JoinTableDTO (String database, String tableName, String primaryColumn, String ownColumn, String joinType){
        super(database,tableName);
        this.primaryColumn = primaryColumn;
        this.ownColumn = ownColumn;
        this.joinType = joinType;

    }
}
