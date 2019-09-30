package cn.jarod.bluecat.core.model.auth;

import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/24
 */
@Setter
@Getter
public class CredentialsDTO {

    private String authority;

    private String password;

    public CredentialsDTO(AuthCredentials auth){
        this.authority = auth.getLoginName();
        this.password = EncryptUtil.stringEncodeSHA256(auth.getPassword());
    }
}
