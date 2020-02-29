package cn.jarod.bluecat.resource.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "operation_log")
public class OperationLogDO  {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition=("bigint(19) comment '自增长Id'"))
    private Long id;


    /**
     * 创建时间
     */
    @Column(nullable = false, columnDefinition=("timestamp default current_timestamp comment '创建时间'"),updatable = false)
    @CreationTimestamp
    private LocalDateTime gmtCreate;

    /**
     * 创建者
     */
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '创建者'"), updatable = false)
    private String operator;

    /**
     *操作员
     */
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '创建者'"), updatable = false)
    private String operatorName;

    /**
     *操作员id
     */
    private String operatorId;

    /**
     *客户端ip
     */
    private String ip;

    /**
     *操作类型
     */
    private String operationType;

    /**
     *操作描述
     */
    private String description;

    /**
     *传入参数
     */
//    private Map<String, Object> parametersMap;

    /**
     *是否操作成功
     */
    private Boolean flag;

}
