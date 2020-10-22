package cn.jarod.bluecat.access.user.pojo;

import cn.jarod.bluecat.core.common.utils.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jarod.jin 2019/9/9
 */
@Getter
@Setter
@ToString
public class SignUpDTO implements Serializable {

    private static final long serialVersionUID = -3611398577384632971L;

    /**注册主体信息*/
    @NotBlank
    private String signName;

    /**密码*/
    @NotBlank
    private String password;

    /**注册类型0-为手机 1为邮箱*/
    @NotNull
    private Integer signType;

    /**用户类型*/
    private Integer credentialType;

    public Integer isTelOrEmail(){
        if (CommonUtil.validTel(signName)){ return 1; }
        if (CommonUtil.validEmail(signName)){ return 2; }
        return 0;
    }


}
