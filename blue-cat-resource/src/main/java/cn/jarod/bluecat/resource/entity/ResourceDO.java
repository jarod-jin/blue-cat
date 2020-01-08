package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
public class ResourceDO extends NoSqlEntity {

    /**访问级别 public protected private*/
    @Field("accessLevel")
    private String accessLevel ;

    /**角色授权*/
    @Field("roleAuthorization")
    private List<String> roleAuthorization;

    /**个体授权*/
    @Field("userAuthorization")
    private List<String> userAuthorization;

    /**角色组授权*/
    @Field("groupAuthorization")
    private List<String> groupAuthorization;

}
