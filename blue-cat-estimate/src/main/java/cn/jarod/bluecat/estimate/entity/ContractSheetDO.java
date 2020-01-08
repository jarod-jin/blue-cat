package cn.jarod.bluecat.estimate.entity;

import cn.jarod.bluecat.core.entity.RdsEntity;
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
 * @author jarod.jin 2019/11/22
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "contract_sheet", indexes = {@Index(columnList ="serialNo", name="SerialNoIndex", unique = true)})
public class ContractSheetDO extends RdsEntity {

    /**合约编号*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '合约编号'"))
    private String serialNo;

    /**客户编号*/
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '客户编号'"))
    private String customerNo;

    /**合约名称*/
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '合约名称'"))
    private String contractName;

    /**合约内容*/
    @Column(columnDefinition=("text comment '合约内容'"))
    private String contractText;

    /**关联系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String belongTo;

}
