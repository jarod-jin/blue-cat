package cn.jarod.bluecat.core.model.auth;

import cn.jarod.bluecat.core.model.BaseDTO;
import org.springframework.util.StringUtils;
import javax.validation.constraints.NotBlank;
import static cn.jarod.bluecat.core.enums.CommonPattern.CN_PHONE_NUMBER;
import static cn.jarod.bluecat.core.enums.CommonPattern.EMAIL;

/**
 * @auther jarod.jin 2019/9/9
 */
public class AuthRegisterDTO extends BaseDTO {

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

    public boolean validTel(){
        return StringUtils.hasText(tel) && tel.matches(CN_PHONE_NUMBER.getPattern());
    }

    public boolean validEmail(){
        return StringUtils.hasText(email) && email.matches(EMAIL.getPattern());
    }

    public boolean hasTelOrEmail(){
        return validTel() || validEmail();
    }



}
