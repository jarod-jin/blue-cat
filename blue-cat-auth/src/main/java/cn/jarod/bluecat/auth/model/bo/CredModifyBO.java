package cn.jarod.bluecat.auth.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @auther jarod.jin 2019/9/18
 */
@Getter
@Setter
public class CredModifyBO implements Serializable {

    private static final long serialVersionUID = 2148576680600970064L;

    private String authority;

    private String currentPassword;

    private String modifiedPassword;

    private String validCode;
}
