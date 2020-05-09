package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.base.entity.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
@Document("application")
public class ApplicationDO extends MongoEntity {

    /**描述*/
    @Field("description")
    private String description;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**版本更新记录*/
    @Field("releases")
    private Map<String,List<ReleaseDO>> releases;

}
