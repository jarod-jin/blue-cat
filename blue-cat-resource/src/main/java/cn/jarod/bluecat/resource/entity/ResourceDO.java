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
@Document("resource")
public class ResourceDO extends NoSqlEntity {

    /**访问级别 public protected private*/
    @Field("accessLevel")
    private String accessLevel;

    /**资源类别*/
    @Field("resourceType")
    private String resourceType;

    /**资源数据*/
    @Field("resourceName")
    private String resourceName;

    /**资源数据*/
    @Field("resourceData")
    private SuperResource resource;

    /**角色授权*/
    @Field("roleAuthorization")
    private List<String> roleAuthorization;

    /**个体授权*/
    @Field("userAuthorization")
    private List<String> userAuthorization;

    /**角色组授权*/
    @Field("groupAuthorization")
    private List<String> groupAuthorization;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
