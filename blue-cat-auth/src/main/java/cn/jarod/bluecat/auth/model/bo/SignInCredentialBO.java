package cn.jarod.bluecat.auth.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/2
 */
@Getter
@Setter
@ToString
public class SignInCredentialBO {

    /**用户唯一标识*/
    private String username;

    /**密码*/
    private String password;

    public SignInCredentialBO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
