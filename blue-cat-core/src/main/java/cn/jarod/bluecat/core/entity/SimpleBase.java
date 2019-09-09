package cn.jarod.bluecat.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @auther jarod.jin 2019/9/9
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
public class SimpleBase {

    //主键
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition=("bigint(19) comment '自增长Id'"))
    private Long id;

    //创建时间
    @Column(nullable = false, columnDefinition=("timestamp default current_timestamp comment '创建时间'"),updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createDate;

    //创建者
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '创建者'"), updatable = false)
    private String creator;

}
