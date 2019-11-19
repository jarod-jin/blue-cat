package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
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
@Table(name = "user_location", indexes = {@Index(columnList ="orgRoleId", name="OrgRoleIndex"),
            @Index(columnList ="username", name="UsernameIndex", unique = true)})
public class UserLocationDO extends BaseEntity {

    //组织角色ID
    @Column(nullable = false, columnDefinition=("bigint(19) comment '组织角色ID'"))
    private Long orgRoleId;


    //用户标识
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户标识'"))
    private String username;


}
