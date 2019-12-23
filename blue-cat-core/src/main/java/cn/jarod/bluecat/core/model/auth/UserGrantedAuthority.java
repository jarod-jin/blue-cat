package cn.jarod.bluecat.core.model.auth;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author jarod.jin 2019/11/8
 */

public class UserGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -256491946697712588L;

    @Getter
    @Setter
    private String sysCode;

    @Getter
    @Setter
    private String terminalVersion;

    private String authority;

    public UserGrantedAuthority(String sysCode, String version){
        this.sysCode = sysCode;
        this.terminalVersion = version;
    }

    public UserGrantedAuthority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
