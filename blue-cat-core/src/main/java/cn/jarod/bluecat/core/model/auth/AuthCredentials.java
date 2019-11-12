package cn.jarod.bluecat.core.model.auth;

import cn.jarod.bluecat.core.utils.Const;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @auther jarod.jin 2019/9/9
 */
@Getter
@Setter
public class AuthCredentials {

    //登录名
    private String signIn;

    //密码
    private String password;

    //系统编号
    private String sysCode;

    //终端版本 ps: IOS 1.0.1 或者Android 1.1.10-beta 或者 PC 1.0 等
    private String terminalVersion;


    public boolean loginValid(){
        if (StringUtils.isEmpty(signIn))
            signIn = Const.SYS_ROOT;
        return StringUtils.isEmpty(signIn) || StringUtils.isEmpty(password) || StringUtils.isEmpty(terminalVersion);
    }


}
