package cn.jarod.bluecat.core.security.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author jarod.jin 2019/11/8
 */

public class GrantedAuthorityDO implements GrantedAuthority {

    private static final long serialVersionUID = -256491946697712588L;

    @Getter
    @Setter
    private String belongTo;

    @Getter
    @Setter
    private String terminalVersion;

    private String authority;

    public GrantedAuthorityDO(String belongTo, String version){
        this.belongTo = belongTo;
        this.terminalVersion = version;
    }

    public GrantedAuthorityDO(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
