package cn.jarod.bluecat.object.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/10
 */
@Getter
@Setter
@ToString
public class ElementDO extends ResourceDO{

    /**元素名称*/
    @Field("subject")
    private String subject;

    /**元素标签内容*/
    @Field("labelText")
    private String labelText;

    /**元素文字内容*/
    @Field("text")
    private String text;

    /**元素图标*/
    @Field("iconPath")
    private String iconPath;

    /**元素地址*/
    @Field("routePath")
    private String routePath;

    /**显示顺序*/
    @Field("sortOrder")
    private Integer sortOrder;

    /**备注说明*/
    @Field("memo")
    private String memo;

}
