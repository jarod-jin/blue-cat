package cn.jarod.bluecat.object.model.bo.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/10
 */
@Getter
@Setter
@ToString
public class CrudPageBO extends CrudElementBO {

    /**布局类型*/
    @Field("layoutType")
    private String layoutType;

    /**下层页面集合*/
    @Field("nextPages")
    private List<CrudPageBO> nextPages;

    /**按钮集合*/
    @Field("buttons")
    private List<CrudElementBO> buttons;
}
