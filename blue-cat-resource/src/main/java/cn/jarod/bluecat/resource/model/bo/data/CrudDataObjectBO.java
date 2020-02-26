package cn.jarod.bluecat.object.model.bo.data;

import cn.jarod.bluecat.core.model.NoSqlModel;
import cn.jarod.bluecat.object.model.bo.CrudAuthorizationBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.Set;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/27
 */
@Getter
@Setter
@ToString
public class CrudDataObjectBO extends NoSqlModel {

    /**对象名称*/
    private String objectName;

    /**对象类型rds（关系型） kv（键值对） document（文档） column（列） graph（图）*/
    private String objectType;

    /**描述*/
    private String description;

    /**备注说明*/
    private String memo;

    /**关联系统编号*/
    private String belongTo;

    /**属性列表*/
    private List<CrudDataObjectBO> properties;

    /**是否控制权限*/
    private Boolean classified;

    /**授权*/
    private Set<CrudAuthorizationBO> authorizations;


}
