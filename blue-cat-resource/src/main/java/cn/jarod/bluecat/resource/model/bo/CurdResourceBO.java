package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.model.NoSqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
public class CurdResourceBO extends NoSqlModel {

    /**访问级别 public protected private*/
    private String accessLevel ;

    /**角色授权*/
    private List<String> roleAuthorization;

    /**个体授权*/
    private List<String> userAuthorization;

    /**角色组授权*/
    private List<String> groupAuthorization;

}
