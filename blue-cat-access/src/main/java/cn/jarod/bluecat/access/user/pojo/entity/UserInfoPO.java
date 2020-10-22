package cn.jarod.bluecat.access.user.pojo.entity;

import cn.jarod.bluecat.core.data.mongo.pojo.MongoPO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * @author jarod.jin 2020-07-09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Document("user_info")
public class UserInfoPO extends MongoPO {

    /**用户标识*/
    @Field(name="username", targetType = FieldType.STRING)
    private String username;

    /**用户昵称*/
    @Field(name="nickname", targetType = FieldType.STRING)
    private String nickname;

    /**名字*/
    @Field(name="first_name", targetType = FieldType.STRING)
    private String firstName;

    /**姓氏*/
    @Field(name="last_name", targetType = FieldType.STRING)
    private String lastName;

    /**性别*/
    @Field(name="gender", targetType = FieldType.INT32)
    private String gender;

    /**头像照片*/
    @Field(name="face_photo", targetType = FieldType.BINARY)
    private String facePhoto;

    /**电话*/
    @Field(name="tel", targetType = FieldType.STRING)
    private String tel;

    /**邮箱*/
    @Field(name="email", targetType = FieldType.STRING)
    private String email;

    /**账号类型 0一般用户，1高级用户 数字越高级别越高*/
    @Field(name="user_type", targetType = FieldType.INT32)
    private Integer userType;

    /**备注说明*/
    @Field(name="memo", targetType = FieldType.IMPLICIT)
    private String memo;

    /**用户密码*/
    @Field(name="credential", targetType = FieldType.IMPLICIT)
    private CredentialPO credential;

    public UserInfoPO(){
        super();
    }

    public UserInfoPO(String username){
        super();
        this.username = username;
    }


}


