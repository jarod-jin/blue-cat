package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
public class ReleaseDO extends MongoEntity {

    /**版本号*/
    @Field("releaseVersion")
    private String releaseVersion;

    /**发布内容*/
    @Field("releaseNote")
    private List<String> notes;

    /**版本序号*/
    @Field("buildNo")
    private BigDecimal buildNo;

    /**下载路径*/
    @Field("downloadPath")
    private String downloadPath;


}
