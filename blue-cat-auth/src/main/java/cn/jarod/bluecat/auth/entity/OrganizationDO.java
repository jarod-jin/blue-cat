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
 * @auther jarod.jin 2019/10/12
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "sys_org", indexes = {@Index(columnList ="orgCode", name="OrgCodeIndex", unique = true)})
public class OrganizationDO extends DataBase {

    //组织编码
    @Column(nullable = false, columnDefinition=("varchar(20) comment '组织编码'"))
    private String orgCode;

    //组织名称
    @Column(nullable = false, columnDefinition=("varchar(30) default '' comment '组织名称'"))
    private String orgName;

    //上级编码
    @Column(nullable = false, columnDefinition=("varchar(20) default '' comment '上级组织编码'"))
    private String parentCode;

    //全组织编码
    @Column(nullable = false, columnDefinition=("varchar(250) comment '全组织编码'"))
    private String fullCode;

    //全组织名称
    @Column(nullable = false, columnDefinition=("varchar(250) comment '全组织名称'"))
    private String fullName;

    //显示顺序
    @Column(nullable = false, columnDefinition=("smallint(5) default 99 comment '显示顺序'"))
    private Integer disOrder;

    //组织类型  0-虚拟组织 1-实际组织
    @Column(nullable = false, columnDefinition=("tinyint(2) default 0 comment '组织类型'"))
    private Integer orgType;

    //关联编号
    @Column(nullable = false, columnDefinition=("varchar(30) default '' comment '关联系统编号'"))
    private String sysCode;
}
