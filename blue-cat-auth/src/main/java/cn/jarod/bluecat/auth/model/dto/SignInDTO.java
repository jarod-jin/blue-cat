package cn.jarod.bluecat.auth.model.dto;

import cn.jarod.bluecat.auth.model.bo.SignInBO;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/24
 */
@Setter
@Getter
public class SignInDTO {

    private String authority;

    private String password;

    public SignInDTO(SignInBO signBO){
        this.authority = signBO.getAuthority();
        this.password = EncryptUtil.stringEncodeSHA256(signBO.getPassword());
    }
}
