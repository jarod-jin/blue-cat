package cn.jarod.bluecat.access.user.entity;

import cn.jarod.bluecat.core.base.entity.MysqlEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author jarod.jin 2019/9/6
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "credential", indexes = {@Index(columnList ="username", name="UsernameIndex", unique = true)})
public class CredentialDO extends MysqlEntity {

    /**用户唯一标识*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String username;

    /**密码*/
    @Column(nullable = false, columnDefinition=("varchar(250) comment '密码'"))
    private String password;

}
