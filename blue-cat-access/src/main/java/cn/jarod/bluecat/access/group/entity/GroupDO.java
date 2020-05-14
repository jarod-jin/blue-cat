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
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/26
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "sys_group", indexes = {@Index(columnList ="groupCode", name="GroupCodeIndex", unique = true)})
public class GroupDO extends MysqlEntity {

    /**用户组编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '用户组编码'"))
    private String groupCode;

    /**用户组名称*/
    @Column(nullable = false, columnDefinition=("varchar(30) default '' comment '用户组名称'"))
    private String groupName;

    /**上级编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) default '' comment '上级组织编码'"))
    private String parentCode;

    /**全组织编码*/
    @Column(nullable = false, columnDefinition=("varchar(255) comment '全组织编码'"))
    private String fullCode;

    /**全组织名称*/
    @Column(nullable = false, columnDefinition=("varchar(255) comment '全组织名称'"))
    private String fullName;

    /**显示顺序*/
    @Column(nullable = false, columnDefinition=("smallint(5) default 99 comment '显示顺序'"))
    private Integer sortOrder;

    /**组织类型  0-虚拟组织 1-实际组织*/
    @Column(nullable = false, columnDefinition=("tinyint(2) default 0 comment '组织类型'"))
    private Integer groupType;

    /**关联资源编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) default '' comment '关联资源编码'"))
    private String businessCode;

    /**所属系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '所属系统编号'"))
    private String belongTo;
}
