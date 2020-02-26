package cn.jarod.bluecat.object.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
@Document("home_page")
public class HomePageDO extends PageDO{

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

    /**终端标识*/
    @Field("terminalType")
    private String terminalType;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
