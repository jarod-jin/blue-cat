package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.model.NoSqlModel;
import cn.jarod.bluecat.resource.entity.SuperResource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author jarod.jin 2019/11/13
 */
@Getter
@Setter
@ToString
public class CrudResourceBO extends NoSqlModel {

    /**访问级别 public protected private*/
    private String accessLevel;

    /**资源类别*/
    private String resourceType;

    /**资源数据*/
    private String resourceName;

    /**资源数据*/
    private SuperResource resource;

    /**角色授权*/
    private List<String> roleAuthorization;

    /**个体授权*/
    private List<String> userAuthorization;

    /**角色组授权*/
    private List<String> groupAuthorization;

    /**关联系统编号*/
    private String belongTo;
}
