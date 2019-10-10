package cn.jarod.bluecat.core.model.auth;

import org.springframework.security.core.GrantedAuthority;

/**
 * @auther jarod.jin 2018/12/3
 */
public class AuthGrantedAuthority implements GrantedAuthority {

    private String authority;

    public AuthGrantedAuthority(String authority) {
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
