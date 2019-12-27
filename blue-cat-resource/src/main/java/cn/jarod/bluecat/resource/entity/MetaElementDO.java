package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author jarod.jin 2019/10/15
 */

@Getter
@Setter
@ToString
@Document("MetaElement")
public class MetaElementDO extends NoSqlEntity {

    /**元素类型*/
    @Field("elementName")
    private String elementName;

    /**元素类型*/
    @Field("elementType")
    private String elementType;

    /**元素地址*/
    @Field("elementUrl")
    private String elementUrl;

    /**关联系统编号*/
    @Field("sysCode")
    private String sysCode;

    /**默认选择*/
    @Field("defaultMark")
    private Boolean defaultMark;

    /**显示顺序*/
    @Field("sortOrder")
    private Integer sortOrder;

    /**说明*/
    @Field("memo")
    private String memo;

}
