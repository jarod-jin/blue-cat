package cn.jarod.bluecat.resource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/27
 */
@Getter
@Setter
@ToString
public class MetadataDO implements SuperResource{

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
    private List<MetadataDO> children;

}
