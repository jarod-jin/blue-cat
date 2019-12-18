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
 * @author jarod.jin 2019/10/14
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "role_resource", indexes = {@Index(columnList ="roleCode", name="OrgCodeIndex")})
public class RoleResourceDO extends BaseEntity {

    /**角色编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '角色编码'"))
    private String roleCode;

    /**资源编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '资源编码'"))
    private String resourceCode;

}
