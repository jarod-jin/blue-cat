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
@Table(name = "role_user", indexes = {@Index(columnList ="orgRoleId", name="OrgRoleIndex")})
public class RoleUserDO extends DataBase {

    //组织角色ID
    @Column(nullable = false, columnDefinition=("bigint(19) comment '组织角色ID'"))
    private Long orgRoleId;

    //用户标识
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户标识'"))
    private String username;


}
