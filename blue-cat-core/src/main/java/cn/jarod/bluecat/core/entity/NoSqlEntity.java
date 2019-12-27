package cn.jarod.bluecat.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jarod.jin 2019/9/6
 */
@Getter
@Setter
public class NoSqlEntity {

    /**主键*/
    @Id
    private ObjectId id;

    /**版本号,乐观锁*/
    @Field("version")
    private Integer version ;

    /**创建时间*/
    @Field("gmtCreate")
    private LocalDateTime gmtCreate;

    /**创建者*/
    @Field("creator")
    private String creator;

    /**修改时间*/
    @Field("gmtModified")
    private LocalDateTime gmtModified;

    /**修改者*/
    @Field("modifier")
    private String modifier;
}
