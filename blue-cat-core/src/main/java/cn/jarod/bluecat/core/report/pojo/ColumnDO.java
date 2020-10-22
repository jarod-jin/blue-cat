package cn.jarod.bluecat.core.report.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDO {

    /**
     * 所在表
     */
    private TableDO table;

    /**
     * 所在表
     */
    private String column;

}
