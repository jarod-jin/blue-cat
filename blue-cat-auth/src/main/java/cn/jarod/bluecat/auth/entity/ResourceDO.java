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
 * @auther jarod.jin 2019/10/15
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "sys_resource", indexes = {@Index(columnList ="resourceType,resourceCode", name="ResourceCodeIndex", unique = true)})
public class ResourceDO extends DataBase {

    //资源编码
    @Column(nullable = false, columnDefinition=("varchar(20) comment '资源编码'"))
    private String resourceCode;

    //资源名称
    @Column(nullable = false, columnDefinition=("varchar(250) default '' comment '资源名称'"))
    private String resourceName;

    //上级编码
    @Column(nullable = false, columnDefinition=("varchar(20) default '' comment '上级编码'"))
    private String parentCode;

    //资源类型
    @Column(nullable = false, columnDefinition=("varchar(10) default '' comment '资源类型'"))
    private String resourceType;

    //资源地址
    @Column(nullable = false, columnDefinition=("varchar(300) default '' comment '资源地址'"))
    private String resourcePath;

    //说明
    @Column(nullable = false, columnDefinition=("varchar(500) default '' comment '说明'"))
    private String memo;

    //关联系统编号
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String sysCode;

    //显示顺序
    @Column(nullable = false, columnDefinition=("smallint(5) default 99 comment '显示顺序'"))
    private Integer disOrder;

}
