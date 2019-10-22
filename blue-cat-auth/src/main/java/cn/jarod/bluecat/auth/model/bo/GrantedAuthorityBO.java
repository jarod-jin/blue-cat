package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.auth.UserOrgBO;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @auther jarod.jin 2018/12/3
 */
public class GrantedAuthorityBO implements GrantedAuthority {

    private static final long serialVersionUID = 306732587010819016L;

    private String authority;

    private List<UserOrgBO> roleList;

    public GrantedAuthorityBO(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
