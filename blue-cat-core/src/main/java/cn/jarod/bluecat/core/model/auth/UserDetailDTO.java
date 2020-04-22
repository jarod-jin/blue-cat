package cn.jarod.bluecat.core.model.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author jarod.jin 2019/9/18
 */
@Getter
@Setter
@ToString
public class UserDetailDTO implements Serializable {

    private static final long serialVersionUID = -4928838767439027864L;

    /**用户标识*/
    private String username;

    /**用户标识*/
    private String password;

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

    /**系统编号*/
    private String belongTo;

    /**终端版本*/
    private String terminalVersion;

    /**组织列表*/
    private List<UserAuthority> roleList;

}
