package cn.jarod.bluecat.auth.model.dto;

import cn.jarod.bluecat.core.model.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * @auther jarod.jin 2019/9/9
 */
@Slf4j
@Getter
@Setter
public class UserInfoDTO extends BaseDTO {

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

}