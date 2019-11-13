package cn.jarod.bluecat.core.model.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @auther jarod.jin 2019/11/12
 */
@Getter
@Setter
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = -8117552658775023461L;
    //用户唯一标识
    @NotBlank
    private String username;

    //用户名
    private String nickname;

    //电话
    private String tel;

    //邮箱
    private String email;

    //性别
    private String gender;

    //照片
    private String photoUrl;

    //系统编号
    private String sysCode;

    //终端编号
    private String terminalVersion;
}
