package cn.jarod.bluecat.core.security.pojo;

import lombok.*;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataShareRuleDO {

    private List<DataConditionDO> conditions;

    private String filter;

    private String operate;

}
