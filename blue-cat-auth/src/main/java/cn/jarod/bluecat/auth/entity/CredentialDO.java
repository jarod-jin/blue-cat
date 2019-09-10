package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.DataBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @auther jarod.jin 2019/9/6
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "credential", indexes = {@Index(columnList ="authority", name="AuthorityIndex", unique = true)})
public class CredentialDO extends DataBase {

    //用户唯一标识
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String authority;

    //密码
    @Column(nullable = false, columnDefinition=("varchar(256) default '' comment '登录密码'"))
    private String password;

    //账号类型
    @Column(nullable = false, columnDefinition=("tinyint default 0 comment '是否删除, 0个人级用户，1系统级用户'"))
    private Integer credentialType;
}
