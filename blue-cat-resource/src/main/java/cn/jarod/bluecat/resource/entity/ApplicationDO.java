package cn.jarod.bluecat.resource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
@Document("application")
public class ApplicationDO extends ResourceDO {

    /**描述*/
    @Field("description")
    private String description;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**web端版本更新记录*/
    @Field("webReleases")
    private List<ReleaseDO> webReleases;

    /**iOs端版本更新记录*/
    @Field("iOsReleases")
    private List<ReleaseDO> iOsReleases;

    /**android端版本更新记录*/
    @Field("androidReleases")
    private List<ReleaseDO> androidReleases;

}
