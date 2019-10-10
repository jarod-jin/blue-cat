package cn.jarod.bluecat.auth.model.bo;

import org.springframework.security.core.GrantedAuthority;

/**
 * @auther jarod.jin 2018/12/3
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authority;

    public CustomGrantedAuthority(String authority) {
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
