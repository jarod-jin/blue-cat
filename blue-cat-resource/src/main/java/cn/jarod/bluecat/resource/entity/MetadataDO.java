package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/27
 */
@Getter
@Setter
@ToString
@Document("Metadata")
public class MetadataDO extends NoSqlEntity {

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

    /**数据名称*/
    @Field("dataName")
    private String dataName;

    /**数据类型*/
    @Field("dataType")
    private String dataType;

    /**描述*/
    @Field("description")
    private String description;

    /**备注说明*/
    @Field("memo")
    private String memo;

    /**子数据集合*/
    @Field("children")
    private List<MetadataDO> childrenData;

}
