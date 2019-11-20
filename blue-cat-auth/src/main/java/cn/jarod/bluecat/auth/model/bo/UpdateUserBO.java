package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @auther jarod.jin 2019/9/9
 */
@Getter
@Setter
public class UpdateUserBO extends BaseModel {

    //用户唯一标识
    @NotBlank
    private String username;

    //实名
    private String realName;

    //身份证号
    private String idCard;

    //网络昵称
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

}
