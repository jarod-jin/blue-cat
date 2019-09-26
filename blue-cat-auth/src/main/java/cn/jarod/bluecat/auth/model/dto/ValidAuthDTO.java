package cn.jarod.bluecat.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @auther jarod.jin 2019/9/9
 */
@Setter
@Getter
public class ValidAuthDTO implements Serializable {

    private static final long serialVersionUID = 7222495133332935145L;

    //登录标识
    private String authority;

    //登录标识可用 true为可用
    private boolean canAuthority = false;

    //电话
    private String tel;

    //电话可用 true为可用
    private boolean canTel = false;

    //邮箱
    private String email;

    //邮箱可用 true为可用
    private boolean canEmail = false;


}
