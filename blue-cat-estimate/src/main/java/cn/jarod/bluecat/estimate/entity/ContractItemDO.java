package cn.jarod.bluecat.estimate.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * @auther jarod.jin 2019/11/22
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "contract_item", indexes = {@Index(columnList ="serialNo", name="SerialNoIndex")})
public class ContractItemDO extends BaseEntity {

    //模板号
    @Column(nullable = false, columnDefinition=("varchar(20) comment '模板号'"))
    private String serialNo;

    //选项内容
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '选项内容'"))
    private String content;

    //得分
    @Column(nullable = false, columnDefinition=("decimal(5,2) default 0.00 comment '得分'"))
    private BigDecimal itemScore;

    //选项列
    @Type(type = "json")
    @Column(nullable = false, columnDefinition=("json comment '选项列'"))
    private List<ConditionDO> conditionJson;

    //描述
    @Column(nullable = false, columnDefinition=("varchar(255) default '' comment '描述'"))
    private String memo;

    //关联系统编号
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String sysCode;

}
