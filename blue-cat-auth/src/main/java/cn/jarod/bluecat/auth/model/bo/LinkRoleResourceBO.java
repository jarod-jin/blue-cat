package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/10/10
 */
@Getter
@Setter
public class LinkRoleResourceBO extends BaseModel {

    private String resourceCode;

    private String roleCode;

}
