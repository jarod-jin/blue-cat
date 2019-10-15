package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.DataBase;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @auther jarod.jin 2019/10/14
 */
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "org_role", indexes = {@Index(columnList ="orgCode", name="OrgCodeIndex")})
public class OrgRoleDO extends DataBase {

    //组织编码
    @Column(nullable = false, columnDefinition=("varchar(20) comment '组织编码'"))
    private String orgCode;

    //角色编码
    @Column(nullable = false, columnDefinition=("varchar(20) comment '角色编码'"))
    private String roleCode;

}
