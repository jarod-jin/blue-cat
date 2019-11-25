package cn.jarod.bluecat.estimate.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @auther jarod.jin 2019/11/25
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "estimate_sheet", indexes = {@Index(columnList ="serialNo", name="SerialNoIndex")})
public class EstimateSheetDO extends BaseEntity {

    //合约编号
    @Column(nullable = false, columnDefinition=("varchar(20) comment '合约编号'"))
    private String serialNo;

    //评估编号
    @Column(nullable = false, columnDefinition=("varchar(20) comment '合约编号'"))
    private String estimateNo;

    //用户唯一标识
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String username;

    //用户名
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '用户名'"))
    private String nickname;

    //总得分
    @Column(nullable = false, columnDefinition=("decimal(5,2) default 0.00 comment '得分'"))
    private BigDecimal totalScore;

    //关联系统编号
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String sysCode;
}
