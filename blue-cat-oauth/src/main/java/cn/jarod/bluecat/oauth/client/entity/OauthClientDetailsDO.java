package cn.jarod.bluecat.oauth.client.entity;


import cn.jarod.bluecat.core.sql.pojo.MsyqlPO;
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
@Table(name = "oauth_client_details", indexes = {@Index(columnList ="clientId", name="ClientIdIndex", unique = true)})
public class OauthClientDetailsDO extends MsyqlPO {
    /**
     * 客户端ID
     * */
    @Column(nullable = false, columnDefinition=("varchar(256) comment '客户端ID'"))
    private String clientId;

    /**
     * 客户端名称
     * */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '客户端名称'"))
    private String clientDescription;

    /**
     * 资源Id集合
     */
    @Column(nullable = false, columnDefinition=("varchar(256) comment '资源Id集合'"))
    private String resourceIds;

    /**
     * 客户端秘钥
     * */
    @Column(nullable = false, columnDefinition=("varchar(256) comment '客户端秘钥'"))
    private String clientSecret;

    /**
     * 重定向地址
     */
    @Column(nullable = false, columnDefinition=("varchar(2000) comment '重定向地址'"))
    private String webServerRedirectUri;


    /**
     * 登录类型
     */
    @Column(nullable = false, columnDefinition=("varchar(256) comment '认证类型'"))
    private String authorizedGrantTypes;


    /**
     * 权限范围
     */
    @Column(nullable = false, columnDefinition=("varchar(200) comment '权限范围'"))
    private String scope;

    /**
     * 权限集合
     */
    @Column(nullable = false, columnDefinition=("varchar(256) comment '权限集合'"))
    private String authorities;

    /**
     * token授权时长，单位：秒
     */
    @Column(nullable = false, columnDefinition=("int(11) comment 'token授权时长，单位：秒'"))
    private Integer accessTokenValidity;

    /**
     * refresh授权时长，单位：秒
     */
    @Column(nullable = false, columnDefinition=("int(11) comment 'refresh授权时长，单位：秒'"))
    private Integer refreshTokenValidity;

    /**
     * 其他信息
     */
    @Column(nullable = false, columnDefinition=("longtext comment '其他信息'"))
    private String additionalInformation;

    /**
     * 自动授权
     */
    @Column(nullable = false, columnDefinition=("varchar(256) comment '自动授权'"))
    private String autoapprove;



}
