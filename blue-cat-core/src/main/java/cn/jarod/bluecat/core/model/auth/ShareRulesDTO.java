package cn.jarod.bluecat.core.model.auth;

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
public class ShareRulesDTO {

    private List<ConditionDTO> conditions;

    private String filter;

    private String operate;

}
