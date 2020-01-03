package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.TreeMap;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
@Document("system")
public class SystemDO extends ResourceDO {

    /**描述*/
    @Field("description")
    private String description;

    /**备注说明*/
    @Field("memo")
    private String memo;

}
