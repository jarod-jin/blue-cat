package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.BaseBO;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/11/8
 */
@Getter
@Setter
public class LinkUserLocationBO extends BaseBO {

    private Long orgRoleId;

    private String username;

}