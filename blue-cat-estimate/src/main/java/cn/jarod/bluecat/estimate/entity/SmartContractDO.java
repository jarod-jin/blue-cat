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

/**
 * @auther jarod.jin 2019/11/22
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "smart_contract", indexes = {@Index(columnList ="serialNo", name="SerialNoIndex", unique = true)})
public class SmartContractDO extends BaseEntity {

    //合约编号
    @Column(nullable = false, columnDefinition=("varchar(20) comment '合约编号'"))
    private String serialNo;

    //客户编号
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '客户编号'"))
    private String customerNo;

    //合约名称
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '合约名称'"))
    private String contractName;

    //描述
    @Column(nullable = false, columnDefinition=("varchar(255) default '' comment '描述'"))
    private String memo;

    //关联系统编号
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String sysCode;

}
