package cn.jarod.bluecat.general.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

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
@Table(name = "release_note", indexes = {@Index(columnList ="terminalType,releaseVersion", name="ReleaseVersionIndex")})
public class ReleaseNoteDO extends BaseEntity {

    /**版本号*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '版本号'"))
    private String releaseVersion;

    /**字典键-值*/
    @Type(type = "json")
    @Column(nullable = false, columnDefinition=("json comment '发布内容'"))
    private List<String> releaseNote;

    /**版本序号*/
    @Column(nullable = false, columnDefinition=("decimal(10,1) default 99 comment '版本序号'"))
    private BigDecimal buildNo;

    /**终端类型*/
    @Column(nullable = false, columnDefinition=("varchar(20) default 'pc' comment '终端类型'"))
    private String terminalType;

    /**关联系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String sysCode;

}
