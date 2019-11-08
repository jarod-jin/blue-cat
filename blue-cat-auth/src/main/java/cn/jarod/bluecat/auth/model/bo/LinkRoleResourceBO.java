package cn.jarod.bluecat.auth.model.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/10/10
 */
@Getter
@Setter
public class LinkRoleResourceBO {

    private String resourceCode;

    private String resourceName;

    private String roleCode;

    private String roleName;

    @Override
    public String toString(){
        return resourceName + "("+ resourceCode + ")" + "-" + roleName + "("+ roleCode +")";
    }
}
