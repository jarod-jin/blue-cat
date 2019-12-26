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
import java.util.Map;

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
@Table(name = "file")
public class FileDO extends BaseEntity {

    /**绝对路径*/
    @Column(nullable = false, columnDefinition=("varchar(250) comment '绝对路径'"))
    private String absolutePath;

    /**相对路径*/
    @Column(nullable = false, columnDefinition=("varchar(250) comment '相对路径'"))
    private String relativePath;

    /**文件名*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '文档文件名'"))
    private String fileName;

    /**原始文件名*/
    @Column(nullable = false, columnDefinition=("varchar(200) comment '原始文件名'"))
    private String originalName ;

    /**后缀*/
    @Column(nullable = false, columnDefinition=("varchar(10) default '' comment '后缀'"))
    private String suffix;

    /**关联系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(30) default 'sys' comment '关联系统编号'"))
    private String sysCode;

}
