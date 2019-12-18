package cn.jarod.bluecat.auth.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/9/24
 */
@Setter
@Getter
@ToString
public class SignInDTO {

    private String loginName;

    private String password;

    private String validCode;
}
