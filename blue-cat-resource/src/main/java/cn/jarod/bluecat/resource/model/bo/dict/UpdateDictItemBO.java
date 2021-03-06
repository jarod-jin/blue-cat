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
public class UpdateDictItemBO extends MongoModel {

    /**字典类别*/
    private String category;

    /**关联系统编号*/
    private String belongTo;

    /**角色名称*/
    private SortedMap<String,Object> items;

}
