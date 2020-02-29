package cn.jarod.bluecat.resource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
public class ResourceDO {

    /**是否控制权限*/
    @Field("classified")
    private Boolean classified;

    /**授权*/
    @Field("authorizations")
    private Set<AuthorizationDO> authorizations;

}
