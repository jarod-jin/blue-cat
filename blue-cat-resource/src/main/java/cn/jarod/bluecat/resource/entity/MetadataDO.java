package cn.jarod.bluecat.resource.entity;

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
 * @version 创建时间：2019/12/27
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "metadata", indexes = {@Index(columnList ="sysCode", name="SysCodeIndex", unique = true)})
public class MetadataDO extends ResourceDO {

    /**系统编码*/
    @Column(nullable = false, columnDefinition=("varchar(32) comment '系统编码'"))
    private String sysCode;

    /**系统名称*/
    @Column(nullable = false, columnDefinition=("varchar(250) default '' comment '系统名称'"))
    private String sysName;

    /**说明*/
    @Column(nullable = false, columnDefinition=("varchar(250) default '' comment '说明'"))
    private String memo;

}
