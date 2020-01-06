package cn.jarod.bluecat.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

/**
 * @author jarod.jin 2019/9/6
 */
@Getter
@Setter
public class NoSqlEntity {

    /**主键*/
    @Id
    protected ObjectId id;

    /**版本号,乐观锁*/
    @Field("version")
    @Version
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

    public NoSqlEntity(){
//        version = 0;
        gmtCreate = gmtModified = LocalDateTime.now();
    }
}
