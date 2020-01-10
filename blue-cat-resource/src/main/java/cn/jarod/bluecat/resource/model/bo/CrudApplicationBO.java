package cn.jarod.bluecat.resource.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
public class CrudApplicationBO extends CurdResourceBO {

    /**描述*/
    private String description;

    /**备注说明*/
    private String memo;

}
