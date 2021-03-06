package cn.jarod.bluecat.resource.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/7
 */
@Setter
@Getter
@ToString
public class ApplicationQuery extends BaseQuery {

    /**描述*/
    private String description;

    public ApplicationQuery(){
        super();
    }
}
