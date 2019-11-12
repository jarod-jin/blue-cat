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

    private static final long serialVersionUID = -256491946697712588L;

    private String sysCode;

    private String terminalVersion;

    public ReqGrantedAuthority(String sysCode, String version){
        this.sysCode = sysCode;
        this.terminalVersion = version;
    }

    @Override
    public String getAuthority() {
        return JSON.toJSONString(this);
    }

}
