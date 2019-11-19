package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
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
@Table(name = "credential", indexes = {@Index(columnList ="username", name="UsernameIndex", unique = true)})
public class CredentialDO extends BaseEntity {

    //用户唯一标识
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String username;

    //密码
    @Column(nullable = false, columnDefinition=("varchar(250) default '' comment '登录密码'"))
    private String password;

}
