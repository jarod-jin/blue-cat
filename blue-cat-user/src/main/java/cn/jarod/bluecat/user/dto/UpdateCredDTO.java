package cn.jarod.bluecat.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author jarod.jin 2019/9/18
 */
@Getter
@Setter
@ToString
public class UpdateCredDTO implements Serializable {

    private static final long serialVersionUID = 2148576680600970064L;

    private String username;

    private String currentPassword;

    private String modifiedPassword;

    private String validCode;
}
