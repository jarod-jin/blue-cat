package cn.jarod.bluecat.core.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

/**
 * @author jarod.jin 2019/9/6
 */
@Getter
@Setter
public class MongoEntity {

    /**主键*/
    @Id
    protected ObjectId id;

    /**版本号,乐观锁*/
    @Field(name = "version",targetType = FieldType.INT32)
    @Version
    private Integer version ;

    /**创建时间*/
    @Field(name = "gmtCreate", targetType = FieldType.TIMESTAMP)
    private LocalDateTime gmtCreate;

    /**创建者*/
    @Field(name = "creator", targetType = FieldType.STRING)
    private String creator;

    /**修改时间*/
    @Field(name = "gmtModified", targetType = FieldType.TIMESTAMP)
    private LocalDateTime gmtModified;

    /**修改者*/
    @Field(name ="modifier",targetType = FieldType.STRING)
    private String modifier;

}
