package cn.jarod.bluecat.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @auther jarod.jin 2019/9/3
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
public class BaseDO {

    //主键
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition=("bigint(19) comment '自增长Id'"))
    private Long id;
    //版本号,乐观锁
    @Version
    @Column(nullable = false, columnDefinition=("int(10) default 0 comment '修改版本号'"))
    private Integer version ;

    //是否删除
    @Column(columnDefinition=("tinyint default 0 comment '是否删除, 0正常，1已删除'"))
    private Integer isDel;

    //修改时间
    @Column(nullable = false, columnDefinition=("timestamp default current_timestamp on update current_timestamp comment '更新时间'"))
    private LocalDateTime modifyDate;

    //修改者
    @Column(columnDefinition=("varchar(50) default '' comment '修改者'"))
    private String modifier;

    //创建时间
    @Column(nullable = false, columnDefinition=("timestamp default current_timestamp comment '创建时间'"),updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createDate;

    //创建者
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '创建者'"), updatable = false)
    private String creator;
}
