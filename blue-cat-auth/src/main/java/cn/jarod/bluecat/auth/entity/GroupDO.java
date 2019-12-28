package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
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
public class GroupDO extends BaseEntity {

    /**用户组编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '用户组编码'"))
    private String groupCode;

    /**用户组名称*/
    @Column(nullable = false, columnDefinition=("varchar(30) default '' comment '用户组名称'"))
    private String groupName;

    /**关联资源编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) default '' comment '关联资源编码'"))
    private String businessCode;

    /**所属系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '所属系统编号'"))
    private String belongTo;
}
