package cn.jarod.bluecat.access.role.pojo.entity;

import cn.jarod.bluecat.core.base.entity.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
@Document("role")
public class RolePO extends MongoEntity {

    /**角色编码*/
    @Field(name="role_code", targetType = FieldType.STRING)
    private String roleCode;

    /**角色名称*/
    @Field(name="role_name", targetType = FieldType.STRING)
    private String roleName;

    /**显示顺序*/
    @Field(name="sort_order", targetType = FieldType.INT32)
    private Integer sortOrder;

    /**关联号*/
    @Field(name="business_code", targetType = FieldType.STRING)
    private String businessCode;

    /**角色描述*/
    @Field(name="memo", targetType = FieldType.STRING)
    private String memo;

}
