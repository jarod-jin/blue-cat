package cn.jarod.bluecat.access.user.entity;

import cn.jarod.bluecat.core.base.entity.MysqlEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author jarod.jin 2019/9/6
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
@Document("credential")
public class CredentialPO extends MongoEntity {

    /**用户ID*/
    @Field(name="user_id", targetType = FieldType.OBJECT_ID)
    private ObjectId userId;

    /**电话*/
    @Field(name="tel", targetType = FieldType.STRING)
    private String tel;

    /**邮箱*/
    @Field(name="email", targetType = FieldType.STRING)
    private String email;

    /**密码*/
    @Field(name="password", targetType = FieldType.STRING)
    private String password;

    /**历史使用密码*/
    @Field(name="history_password", targetType = FieldType.ARRAY)
    private String historyPassword;

}
