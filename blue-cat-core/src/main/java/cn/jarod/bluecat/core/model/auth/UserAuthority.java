package cn.jarod.bluecat.core.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @auther jarod.jin 2019/11/8
 */
@Getter
@Setter
public class UserAuthority implements Serializable {

    private static final long serialVersionUID = 8394858769801484423L;

    private String orgCode;

    private String orgName;

    private Integer orgType;

    private String roleCode;

    private String roleName;

    public boolean isAuth(){
        return StringUtils.hasText(orgName) && StringUtils.hasText(roleName);
    }

}
