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
@Table(name = "resource_share", indexes = {@Index(columnList ="shareCode", name="ShareIndex")})
public class ResourceShareDO extends BaseEntity {

    /**分享编码*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '分享编码'"))
    private String shareCode;

    /**分享编码 0-角色 1-用户组 2-个人*/
    @Column(nullable = false, columnDefinition=("smallint(4) default 0 comment '分享编码'"))
    private Integer shareType;

    /**资源编码*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '资源编码'"))
    private String resourceCode;

    public ResourceShareDO(){

    }

    public ResourceShareDO(String shareCode,Integer shareType){
        this.shareCode = shareCode;
        this.shareType = shareType;
    }


    public ResourceShareDO(String resourceCode,String shareCode,Integer shareType){
        this.resourceCode = resourceCode;
        this.shareCode = shareCode;
        this.shareType = shareType;
    }

}
