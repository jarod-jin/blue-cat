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
 * 授权记录
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
@Table(name = "oauth_approval", indexes = {@Index(columnList ="clientId,username", name="ClientIdAndUsernameIndex")})
public class OauthApprovalDO extends MsyqlPO {

    /**
     * 客户端ID
     * */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '客户端ID'"))
    private String clientId;

    /**
     * '登录的用户名
     * */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '登录的用户名'"))
    private String username;

    /**
     * '过期时间'
     */
    @Column(nullable = false, columnDefinition=("datetime comment '过期时间'"))
    private String gmtExpire;


    /**
     * 状态
     */
    @Column(nullable = false, columnDefinition=("varchar(10) comment '状态（Approve或Deny）'"))
    private String approvalStatus;


    /**
     * 作用域
     */
    @Column(nullable = false, columnDefinition=("varchar(200) comment '申请的权限范围'"))
    private String scope;

}
