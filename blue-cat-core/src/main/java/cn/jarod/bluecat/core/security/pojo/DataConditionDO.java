package cn.jarod.bluecat.core.security.pojo;

import lombok.*;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataConditionDO {

    /**
     * 数据库名
     */
    private String database;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类别
     */
    private String dataType;

    /**
     * 查询方式
     */
    private String operator;

    /**
     * 字段值
     */
    private String fieldValue;

    public DataConditionDO(String fieldName, String dataType, String operator, String fieldValue){
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.operator = operator;
        this.fieldValue = fieldValue;
    }

}
