package cn.jarod.bluecat.estimate.entity;

import cn.jarod.bluecat.core.sql.pojo.MsyqlPO;
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
import java.math.BigDecimal;

/**
 * @author jarod.jin 2019/11/25
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "estimate_sheet", indexes = {@Index(columnList ="serialNo", name="SerialNoIndex")})
public class EstimateSheetDO extends MsyqlPO {

    /**合约编号*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '合约编号'"))
    private String serialNo;

    /**用户唯一标识*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String username;

    /**用户名*/
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '用户名'"))
    private String nickname;

    /**是否完成*/
    @Column(nullable = false, columnDefinition=("tinyint(1) default 0 comment '是否完成 0-未完成 1-已完成'"))
    private Integer finishMark;

    /**总得分*/
    @Column(nullable = false, columnDefinition=("decimal(5,2) default 0.00 comment '得分'"))
    private BigDecimal totalScore;

    /**关联系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String belongTo;
}
