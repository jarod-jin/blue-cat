package cn.jarod.bluecat.access.group.pojo;

import cn.jarod.bluecat.core.api.pojo.MysqlDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/4
 */
@Getter
@Setter
@ToString
public class LinkOrgRoleBO extends MysqlDO {

    private String orgCode;

    private String roleCode;

    private String roleName;

    private Integer sortOrder;

    private String memo;
}
