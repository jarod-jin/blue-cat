package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/10/14
 */
@Document
@Getter
@Setter
@ToString
public class ReleaseNoteDO extends NoSqlEntity {

    /**版本号*/
    @Field("releaseVersion")
    private String releaseVersion;

    /**发布内容*/
    @Field("releaseNote")
    private List<String> releaseNote;

    /**版本序号*/
    @Field("buildNo")
    private BigDecimal buildNo;

    /**终端类型*/
    @Field("terminalType")
    private String terminalType;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
