package cn.jarod.bluecat.access.group.entity;

import cn.jarod.bluecat.core.base.entity.MongoEntity;
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
@Table(name = "group_role", indexes = {@Index(columnList ="username", name="UsernameIndex")})
public class GroupRoleDO extends MysqlEntity {

    /**
     * 用户组编码
     */
    @Column(nullable = false, columnDefinition=("varchar(20) comment '用户组编码'"))
    private String groupCode;

    /**
     * 角色编码
     */
    @Column(nullable = false, columnDefinition=("varchar(20) comment '角色编码'"))
    private String roleCode;

    /**
     * 说明备注
     */
    @Column(nullable = false, columnDefinition=("varchar(255) default '' comment '角色编码'"))
    private String memo;

}
