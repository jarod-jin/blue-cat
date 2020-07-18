package cn.jarod.bluecat.access.group.pojo.entity;

import cn.jarod.bluecat.core.base.entity.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/26
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
@Document("group")
public class GroupPO extends MongoEntity {

    /**用户组编码*/
    @Field(name="group_code", targetType = FieldType.STRING)
    private String groupCode;

    /**用户组名称*/
    @Field(name="group_name", targetType = FieldType.STRING)
    private String groupName;

    /**显示顺序*/
    @Field(name="sort_order", targetType = FieldType.INT32)
    private Integer sortOrder;

    /**关联号*/
    @Field(name="business_code", targetType = FieldType.STRING)
    private String businessCode;

    /**下级组*/
    @Field(name="sub_groups", targetType = FieldType.ARRAY)
    private List<GroupPO> subGroups;

    /**角色组*/
    @Field(name="group_roles", targetType = FieldType.ARRAY)
    private List<ObjectId> groupRoles;
}
