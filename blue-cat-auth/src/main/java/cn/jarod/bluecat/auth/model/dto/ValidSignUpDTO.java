package cn.jarod.bluecat.auth.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author jarod.jin 2019/9/9
 */
@Setter
@Getter
@ToString
public class ValidSignUpDTO implements Serializable {

    private static final long serialVersionUID = 7222495133332935145L;

    /**登录标识*/
    private String username;

    /**登录标识可用 true为可用*/
    private Boolean canUsername;

    /**电话*/
    private String tel;

    /**电话可用 true为可用*/
    private Boolean canTel;

    /**邮箱*/
    private String email;

    /**邮箱可用 true为可用*/
    private Boolean canEmail;


}
