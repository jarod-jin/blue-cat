package cn.jarod.bluecat.core.model.auth;

import cn.jarod.bluecat.core.model.BaseDTO;

import javax.validation.constraints.NotBlank;

/**
 * @auther jarod.jin 2019/9/9
 */
public class AuthorityDTO extends BaseDTO {

    //用户唯一标识
    @NotBlank
    private String authority;

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

    //说明
    private String memo;

    //用户类型
    private Integer credentialType;

}
