package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.DataBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @auther jarod.jin 2019/10/12
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "organization", indexes = {@Index(columnList ="orgCode", name="orgCodeIndex", unique = true)})
public class OrganizationDO extends DataBase {

    //组织编码
    @Column(nullable = false, columnDefinition=("varchar(20) comment '组织编码'"))
    private String orgCode;

    //组织名称
    @Column(nullable = false, columnDefinition=("varchar(256) default '' comment '组织名称'"))
    private String orgName;

    //上级编码
    @Column(nullable = false, columnDefinition=("varchar(20) default '0' comment '上级组织编码'"))
    private String parentCode;

    //全组织编码
    @Column(nullable = false, columnDefinition=("varchar(250) comment '全组织编码'"))
    private String fullCode;

    //全组织名称
    @Column(nullable = false, columnDefinition=("varchar(250) comment '全组织名称'"))
    private String fullName;




}
