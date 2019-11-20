package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/11/4
 */
@Getter
@Setter
public class LinkOrgRoleBO extends BaseModel {

    private String orgCode;

    private String roleCode;

    private String roleName;

    private Integer disOrder;

    private String memo;
}
