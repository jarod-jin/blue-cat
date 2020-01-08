package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.RdsModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/8
 */
@Getter
@Setter
@ToString
public class LinkUserLocationBO extends RdsModel {

    private Long orgRoleId;

    private String username;

}
