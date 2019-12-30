package cn.jarod.bluecat.resource.entity;

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
@Document("dict")
public class DictDO extends ResourceDO {

    /**描述*/
    @Field("description")
    private String description;

    /**字典*/
    @Field("category")
    private String category;

    /**字典键-值*/
    @Field("items")
    private TreeMap<String,Object> items;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
