package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.model.NoSqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
public class CurdResourceBO {

    /**是否控制权限*/
    private Boolean classified;

    /**授权*/
    private Set<CrudAuthorizationBO> authorizations;

}
