package cn.jarod.bluecat.access.user.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/8
 */
@Getter
@Setter
@ToString
public class LinkUserLocationBO extends MysqlModel {

    private Long orgRoleId;

    private String username;

}
