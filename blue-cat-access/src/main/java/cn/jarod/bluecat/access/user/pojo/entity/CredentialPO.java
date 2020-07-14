package cn.jarod.bluecat.access.user.pojo.entity;

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
 * @author jarod.jin 2019/9/6
 */
@Getter
@Setter
@ToString
public class CredentialPO  {

    /**密码*/
    @Field(name="password", targetType = FieldType.STRING)
    private String password;

    /**历史使用密码*/
    @Field(name="history_password", targetType = FieldType.ARRAY)
    private List<String> historyPassword;

}
