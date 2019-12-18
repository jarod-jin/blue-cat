package cn.jarod.bluecat.core.model.auth;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author jarod.jin 2019/11/8
 */
@Getter
@Setter
public class UserGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -256491946697712588L;

    private String sysCode;

    private String terminalVersion;

    private UserAuthority userAuthority;

    public UserGrantedAuthority(String sysCode, String version){
        this.sysCode = sysCode;
        this.terminalVersion = version;
    }

    public UserGrantedAuthority(UserAuthority userAuthority){
        this.userAuthority = userAuthority;
    }

    @Override
    public String getAuthority() {
        return JSON.toJSONString(userAuthority);
    }

}
