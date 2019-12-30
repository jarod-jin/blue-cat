package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
@Document("MetaElement")
public class MetaElementDO extends ResourceDO {

    /**元素类型*/
    @Field("elementName")
    private String elementName;

    /**元素类型*/
    @Field("elementType")
    private String elementType;

    /**元素地址*/
    @Field("elementUrl")
    private String elementUrl;

    /**是否选中*/
    @Field("selected")
    private Boolean selected;

    /**显示顺序*/
    @Field("sortOrder")
    private Integer sortOrder;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**子数据集合*/
    @Field("children")
    private List<MetaElementDO> children;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
