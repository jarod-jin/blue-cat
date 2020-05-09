package cn.jarod.bluecat.resource.model.bo.dict;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.SortedMap;

/**
 * @author jarod.jin 2019/11/19
 */
@Getter
@Setter
@ToString
public class CrudDictBO extends MongoModel {

    /**字典*/
    private String category;

    /**描述*/
    private String description;

    /**字典键-值*/
    private SortedMap<String,Object> items;

    /**备注说明*/
    private String memo;

    /**关联系统编号*/
    private String belongTo;

}
