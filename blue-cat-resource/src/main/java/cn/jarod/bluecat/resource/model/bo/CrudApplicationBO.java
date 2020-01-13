package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.model.NoSqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
public class CrudApplicationBO extends NoSqlModel {

    /**描述*/
    private String description;

    /**备注说明*/
    private String memo;

    /**是否控制权限*/
    private Boolean classified;

    /**授权*/
    private Set<CrudAuthorizationBO> authorizations;


}
