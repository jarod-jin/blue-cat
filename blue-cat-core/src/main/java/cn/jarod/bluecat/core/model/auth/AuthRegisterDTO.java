package cn.jarod.bluecat.core.model.auth;

import cn.jarod.bluecat.core.model.BaseDTO;
import cn.jarod.bluecat.core.utils.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @auther jarod.jin 2019/9/9
 */
@Getter
@Setter
@ToString
public class AuthRegisterDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -3611398577384632971L;

    //用户唯一标识
    @NotBlank
    private String authority;

    //密码
    @NotBlank
    private String password;

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


    public boolean hasTelOrEmail(){
        return CommonUtil.validTel(tel) || CommonUtil.validEmail(email);
    }



}
