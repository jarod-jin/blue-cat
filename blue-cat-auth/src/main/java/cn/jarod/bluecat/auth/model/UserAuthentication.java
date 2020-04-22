package cn.jarod.bluecat.auth.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/4/22
 */
@Data
public class UserAuthentication implements Serializable {

    private static final long serialVersionUID = 7977941839244164396L;

    /**用户标识*/
    private String username;

    /**用户名*/
    private String nickname;

    /**电话*/
    private String tel;

    /**邮箱*/
    private String email;

    /**性别*/
    private String gender;

    /**照片*/
    private String photoUrl;

}
