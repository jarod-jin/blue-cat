package cn.jarod.bluecat.auth.model.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/11/8
 */
@Getter
@Setter
public class RequestAuthorityBO {

    private String orgCode;

    private String orgName;

    private String fullCode;

    private String fullName;

    private String roleCode;

    private String roleName;

    private Integer disOrder;
}
