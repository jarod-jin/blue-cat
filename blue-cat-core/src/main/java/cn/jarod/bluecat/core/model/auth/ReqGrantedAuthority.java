package cn.jarod.bluecat.core.model.auth;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @auther jarod.jin 2019/11/8
 */
@Getter
@Setter
public class ReqGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 8394858769801484423L;

    private String orgCode;

    private String orgName;

    private String fullCode;

    private String fullName;

    private String roleCode;

    private String roleName;

    private Integer disOrder;

    @Override
    public String getAuthority() {
        return JSON.toJSONString(this);
    }
}