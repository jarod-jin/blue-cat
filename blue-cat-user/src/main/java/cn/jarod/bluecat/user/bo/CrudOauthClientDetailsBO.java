package cn.jarod.bluecat.user.bo;

import cn.jarod.bluecat.core.model.MysqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/2
 */
@Setter
@Getter
@ToString
public class CrudOauthClientDetailsBO extends MysqlModel {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     * */
    private String clientDescription;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 重定向地址
     */
    private String redirectUrl;


    /**
     * 登录类型
     */
    private String grantType;


    /**
     * 权限范围
     */
    private String scope;
}
