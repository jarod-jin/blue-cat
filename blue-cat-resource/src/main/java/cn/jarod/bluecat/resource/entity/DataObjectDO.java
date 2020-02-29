package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/27
 */
@Getter
@Setter
@ToString
@Document("data_object")
public class DataObjectDO extends MongoEntity {

    /**对象名称*/
    @Field("objectName")
    private String objectName;

    /**对象类型rds（关系型） kv（键值对） document（文档） column（列） graph（图）*/
    @Field("objectType")
    private String objectType;

    /**描述*/
    @Field("description")
    private String description;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

    /**属性列表*/
    @Field("properties")
    private List<DataPropertyDO> properties;

    /**是否控制权限*/
    @Field("classified")
    private Boolean classified;

    /**授权*/
    @Field("authorizations")
    private Set<AuthorizationDO> authorizations;


}
