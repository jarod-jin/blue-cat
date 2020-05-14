package cn.jarod.bluecat.access.group.entity;

import cn.jarod.bluecat.core.base.entity.MysqlEntity;
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
 * @author jarod.jin 2019/10/14
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "user_group", indexes = {@Index(columnList ="username", name="UsernameIndex")})
public class UserGroupDO extends MysqlEntity {

    /**用户标识*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户标识'"))
    private String username;

    /**用户组编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '用户组编码'"))
    private String groupCode;

}
