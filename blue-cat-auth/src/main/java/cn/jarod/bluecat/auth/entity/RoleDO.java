package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.DataBase;
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
 * @auther jarod.jin 2019/10/14
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "sys_role", indexes = {@Index(columnList ="roleCode", name="RoleCodeIndex", unique = true)})
public class RoleDO extends DataBase {

    //角色编码
    @Column(nullable = false, columnDefinition=("varchar(20) comment '角色编码'"))
    private String roleCode;

    //角色名称
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '角色名称'"))
    private String roleName;

    //显示顺序
    @Column(nullable = false, columnDefinition=("smallint(5) default 99 comment '显示顺序'"))
    private Integer disOrder;

    //角色描述
    @Column(nullable = false, columnDefinition=("varchar(500) default '' comment '角色描述'"))
    private String memo;


}