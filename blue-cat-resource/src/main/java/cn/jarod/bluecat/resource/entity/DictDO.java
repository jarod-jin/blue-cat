package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.SortedMap;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
@Document("dict")
public class DictDO extends MongoEntity {

    /**字典*/
    @Field("category")
    private String category;

    /**描述*/
    @Field("description")
    private String description;

    /**字典键-值*/
    @Field("items")
    private SortedMap<String,Object> items;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;



}
