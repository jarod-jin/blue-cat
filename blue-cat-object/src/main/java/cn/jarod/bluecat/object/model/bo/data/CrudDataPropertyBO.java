package cn.jarod.bluecat.object.model.bo.data;

import cn.jarod.bluecat.object.model.bo.CurdResourceBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/10
 */
@Getter
@Setter
@ToString
public class CrudDataPropertyBO extends CurdResourceBO {

    /**属性名称*/
    private String propertyName;

    /**属性类型*/
    private String propertyType;

    /**描述*/
    private String description;

    /**备注说明*/
    private String memo;
}
