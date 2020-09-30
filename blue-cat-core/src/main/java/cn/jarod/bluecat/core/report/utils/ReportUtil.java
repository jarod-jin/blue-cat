package cn.jarod.bluecat.report.utils;

import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.model.auth.ConditionDTO;
import cn.jarod.bluecat.core.model.auth.ShareRulesDTO;
import cn.jarod.bluecat.report.pojo.ColumnDTO;
import cn.jarod.bluecat.report.pojo.ConditionConfigDTO;
import cn.jarod.bluecat.report.pojo.ReportItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wb_zw16
 * @date 2020/8/15 17:37
 */
@Slf4j
public class ReportUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static void subReportDate(List<Map<String, Object>> result, String... columns) {
        if (result == null || columns == null) {
            return;
        }
        result.forEach(item -> {
            for (int i = 0; i < columns.length; i++) {
                String column = columns[i];
                if (item.get(column) != null && StringUtils.hasText(item.get(column).toString())) {
                    try {
//                        item.put(column, item.get(column).toString().substring(0, 10));
                        item.put(column, sdf.format(item.get(column)));
                    } catch (Exception e) {
                        log.error("date sub error >>> message:{}", e.getMessage());
                    }
                }
            }
        });
    }

    public static ShareRulesDTO assembleConditions(Set<ColumnDTO> columnSet, List<ConditionDTO> condition, ReportItemDTO reportItemDTO) {
        if(!StringUtils.isEmpty(reportItemDTO.getFilterLogic())){
            return assembleFilterLogicConditions(columnSet, condition, reportItemDTO);
        }
        if (!CollectionUtils.isEmpty(reportItemDTO.getConditions())) {
            Map<String, ConditionConfigDTO> map = reportItemDTO.getConditions().stream().collect(Collectors.toMap(ConditionConfigDTO::getFieldname, val -> val, (v1, v2) -> v1));
            ArrayList<String> keys = new ArrayList<>(map.keySet());
            columnSet.forEach(column -> {
                if (keys.contains(column.getColumn())) {
                    ConditionConfigDTO configDTO = map.get(column.getColumn());
                    condition.add(new ConditionDTO(column.getTable().getDatabase(), column.getTable().getTableName(), configDTO.getFieldname(), null, configDTO.getOperatorName(), configDTO.getFieldValue()));
                }
            });
        }
        ShareRulesDTO queryRules = new ShareRulesDTO();
        if (!CollectionUtils.isEmpty(condition)) {
            StringBuilder builder = new StringBuilder();
            for (int i = condition.size(); i >= 1; i--) {
                builder.append(Constant.Symbol.NUMBER_SIGN + i).append(" AND ");
            }
            queryRules.setFilter(builder.substring(0, builder.length() - 5));
        }
        queryRules.setConditions(condition);
        return queryRules;
    }

    private static ShareRulesDTO assembleFilterLogicConditions(Set<ColumnDTO> columnSet, List<ConditionDTO> condition, ReportItemDTO reportItemDTO) {
        ShareRulesDTO queryRules = new ShareRulesDTO();

        List<ConditionDTO> conditions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(reportItemDTO.getConditions())) {
            reportItemDTO.getConditions().forEach(c ->{
                columnSet.forEach(column -> {
                    if (column.getColumn().equals(c.getFieldname())) {
                        conditions.add(new ConditionDTO(column.getTable().getDatabase(), column.getTable().getTableName(),  c.getFieldname(), null, c.getOperatorName(), c.getFieldValue()));
                        return;
                    }
                });
            });
        }

        if (!CollectionUtils.isEmpty(condition)) {
            StringBuilder builder = new StringBuilder(reportItemDTO.getFilterLogic());
            for (int i = 0; i < condition.size(); i++) {
                builder.append(" ").append("AND").append(" #").append(conditions.size()+1);
                conditions.add(condition.get(i));
            }
            queryRules.setFilter(builder.toString());
        }

        queryRules.setConditions(conditions);
        return queryRules;
    }
}
