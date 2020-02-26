package cn.jarod.bluecat.object.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/10
 */
@Getter
@Setter
@ToString
public class DataPropertyDO extends ResourceDO {

    /**属性名称*/
    @Field("propertyName")
    private String propertyName;

    /**属性类型*/
    @Field("propertyType")
    private String propertyType;

    /**描述*/
    @Field("description")
    private String description;

    /**备注说明*/
    @Field("memo")
    private String memo;
}
