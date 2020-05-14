package cn.jarod.bluecat.access.role.pojo;

import cn.jarod.bluecat.core.base.model.MysqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/4
 */
@Getter
@Setter
@ToString
public class CrudRoleBO extends MysqlModel {

    private String roleCode;

    private String roleName;

    private Integer sortOrder;

    private String memo;

}
