package cn.jarod.bluecat.core.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jarod.jin 2019/9/6
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class), @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class RdsEntity {

    /**主键*/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition=("bigint(19) comment '自增长Id'"))
    private Long id;

    /**版本号,乐观锁*/
    @Version
    @Column(nullable = false, columnDefinition=("int(10) default 0 comment '修改版本号'"))
    private Integer version ;

    /**是否删除*/
    @Column(nullable = false, columnDefinition=("tinyint default 0 comment '是否删除, 0正常，1已删除'"))
    private Integer isDel;

    /**创建时间*/
    @Column(nullable = false, columnDefinition=("timestamp default current_timestamp comment '创建时间'"),updatable = false)
    @CreationTimestamp
    private LocalDateTime gmtCreate;

    /**创建者*/
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '创建者'"), updatable = false)
    private String creator;

    /**修改时间*/
    @Column(nullable = false, columnDefinition=("timestamp default current_timestamp on update current_timestamp comment '更新时间'"))
    private LocalDateTime gmtModified;

    /**修改者*/
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '修改者'"))
    private String modifier;
}
