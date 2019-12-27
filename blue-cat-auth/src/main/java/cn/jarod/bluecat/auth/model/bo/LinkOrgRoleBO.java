package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/4
 */
@Getter
@Setter
@ToString
public class LinkOrgRoleBO extends BaseModel {

    private String orgCode;

    private String roleCode;

    private String roleName;

    private Integer sortOrder;

    private String memo;
}
