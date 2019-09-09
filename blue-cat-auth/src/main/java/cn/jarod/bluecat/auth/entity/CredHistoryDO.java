package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.SimpleBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @auther jarod.jin 2019/9/9
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "cred_history", indexes = {@Index(columnList ="authority", name="AuthorityIndex")})
public class CredHistoryDO extends SimpleBase {

    //登录名
    @Column(nullable = false, columnDefinition=("varchar(50) comment '登录名'"))
    private String authority;

    //密码验证串
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '密码验证串'"))
    private String password;

}
