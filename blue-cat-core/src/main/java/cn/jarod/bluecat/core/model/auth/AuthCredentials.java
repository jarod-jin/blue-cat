package cn.jarod.bluecat.core.model.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/9
 */
@Getter
@Setter
public class AuthCredentials {

    //登录名
    private String loginName;

    //密码
    private String password;

    //终端类型 IOS Android PC
    private String terminalType;

}
