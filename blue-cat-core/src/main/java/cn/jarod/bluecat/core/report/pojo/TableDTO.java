package cn.jarod.bluecat.report.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {

    /**
     * 库名
     */
    private String database;

    /**
     * 表名
     */
    private String tableName;
}
