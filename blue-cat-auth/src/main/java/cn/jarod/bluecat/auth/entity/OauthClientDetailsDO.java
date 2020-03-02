package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.MysqlEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 授权客户端信息
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/2
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "oauth_client_info", indexes = {@Index(columnList ="clientId", name="ClientIdIndex", unique = true)})
public class OauthClientDetailsDO extends MysqlEntity {

    /**
     * 客户端ID
     * */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '客户端ID'"))
    private String clientId;

    /**
     * 客户端名称
     * */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '客户端名称'"))
    private String clientDescription;

    /**
     * 客户端秘钥
     * */
    @Column(nullable = false, columnDefinition=("varchar(250) comment '客户端秘钥'"))
    private String clientSecret;

    /**
     * 重定向地址
     */
    @Column(nullable = false, columnDefinition=("varchar(2000) comment '重定向地址'"))
    private String redirectUrl;


    /**
     * 登录类型
     */
    @Column(nullable = false, columnDefinition=("varchar(200) comment '客户端秘钥'"))
    private String grantType;


    /**
     * 权限范围
     */
    @Column(nullable = false, columnDefinition=("varchar(200) comment '权限范围'"))
    private String scope;
}
