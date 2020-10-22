package cn.jarod.bluecat.access.group.pojo.entity;


import cn.jarod.bluecat.core.sql.pojo.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
@Document("group_role")
public class GroupRolePO extends MongoEntity {

    /**用户组编码*/
    @Field(name="group_code", targetType = FieldType.STRING)
    private String groupCode;

    /**角色编码*/
    @Field(name="role_code", targetType = FieldType.STRING)
    private String roleCode;

    @Field(name="user_ids", targetType = FieldType.ARRAY)
    private List<ObjectId> userIds;

}
