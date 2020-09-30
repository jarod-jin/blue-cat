package cn.jarod.bluecat.report.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDTO {

    /**
     * 所在表
     */
    private TableDTO table;

    /**
     * 所在表
     */
    private String column;

}
