package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;


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

    /**版本更新记录*/
    @Field("releases")
    private Map<String,List<ReleaseDO>> releases;

}
