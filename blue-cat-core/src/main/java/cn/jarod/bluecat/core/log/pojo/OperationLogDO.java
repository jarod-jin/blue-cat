package cn.jarod.bluecat.core.log.pojo;

import cn.jarod.bluecat.core.data.sql.pojo.MsyqlPO;
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
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "operation_log", indexes = {@Index(columnList ="objectName", name="ObjectNameIndex")})
public class OperationLogDO extends MsyqlPO {

    /**
     *操作对象
     */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '操作对象'"))
    private String objectName;

    /**
     *操作员
     */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '操作员'"))
    private String operator;

    /**
     *客户端ip
     */
    @Column(nullable = false, columnDefinition=("bigint(20) default 0 comment '客户端ip'"))
    private Long ip;

    /**
     *操作类型
     */
    @Column(nullable = false, columnDefinition=("varchar(50) comment '操作类型'"))
    private String operationType;

    /**
     *操作描述
     */
    @Column(nullable = false, columnDefinition=("varchar(255) comment '操作描述'"))
    private String description;

    /**
     *传入参数
     */
    @Type(type = "json")
    @Column(columnDefinition=("json comment '传入参数'"))
    private Map<String, String[]> params;

    /**
     *是否操作成功
     */
    @Column(nullable = false, columnDefinition=("tinyint(1) comment '是否操作成功'"))
    private Boolean flag;

}
