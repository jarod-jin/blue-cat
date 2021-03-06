package cn.jarod.bluecat.estimate.entity;

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
import java.math.BigDecimal;
import java.util.List;

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
@Table(name = "estimate_item", indexes = {@Index(columnList ="serialNo", name="SerialNoIndex")})
public class EstimateItemDO extends MsyqlPO {

    //合约编号
    @Column(nullable = false, columnDefinition=("varchar(20) comment '合约编号'"))
    private String serialNo;

    //评估编号
    @Column(nullable = false, columnDefinition=("bigint(19) comment '评估单Id'"))
    private Long estimateSheetId;

    //条目序号
    @Column(nullable = false, columnDefinition=("int(5) default 99 comment '条目序号'"))
    private Integer itemNo;

    //得分
    @Column(nullable = false, columnDefinition=("decimal(5,2) default 0.00 comment '得分'"))
    private BigDecimal itemScore;

    //回答列
    @Type(type = "json")
    @Column(nullable = false, columnDefinition=("json comment '回答列'"))
    private List<AnswerDO> answerJson;

    //关联系统编号
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String belongTo;


}
