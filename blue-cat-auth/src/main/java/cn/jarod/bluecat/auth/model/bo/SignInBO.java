package cn.jarod.bluecat.auth.model.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/24
 */
@Setter
@Getter
public class SignInBO {

    private String loginName;

    private String password;

    private String validCode;
}
