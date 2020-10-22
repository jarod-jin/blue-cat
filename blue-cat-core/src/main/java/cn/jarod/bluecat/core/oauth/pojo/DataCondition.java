package cn.jarod.bluecat.core.oauth.pojo;

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
public class DataCondition {

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
     * 数据库名
     */
    private String dataType;

    /**
     * 数据类型名
     */
    private String operator;

    /**
     * 字段值
     */
    private String fieldValue;

    public DataCondition(String fieldName, String dataType, String operator, String fieldValue){
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.operator = operator;
        this.fieldValue = fieldValue;
    }

}
