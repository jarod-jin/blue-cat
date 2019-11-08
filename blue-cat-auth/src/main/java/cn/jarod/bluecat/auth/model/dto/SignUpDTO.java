package cn.jarod.bluecat.auth.model.dto;

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
public class SignUpDTO implements Serializable {

    private static final long serialVersionUID = -3611398577384632971L;

    //用户唯一标识
    @NotBlank
    private String loginName;

    //密码
    @NotBlank
    private String password;

    //电话
    private String tel;

    //邮箱
    private String email;

    //用户类型
    private Integer credentialType;

    public boolean hasTelOrEmail(){
        return CommonUtil.validTel(tel) || CommonUtil.validEmail(email);
    }


}
