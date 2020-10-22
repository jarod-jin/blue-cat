package cn.jarod.bluecat.core.report.utils;


import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.security.pojo.DataConditionDO;
import cn.jarod.bluecat.core.security.pojo.DataShareRuleDO;
import cn.jarod.bluecat.core.common.utils.ForEachUtil;
import cn.jarod.bluecat.core.report.pojo.ColumnDO;
import cn.jarod.bluecat.core.report.pojo.ConditionConfigDO;
import cn.jarod.bluecat.core.report.pojo.ReportItemDO;
import cn.jarod.bluecat.core.report.pojo.ReportObjectDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ReportUtil {

    /**
     * 生成报表sql
     * @param reportObject
     * @return
     */
    public static String sqlHandler(ReportObjectDO reportObject){
        StringBuffer sql = new StringBuffer().append("select");
        reportObject.getColumns().forEach(c->
            sql.append(Constant.SqlSymbol.SPACE)
                .append(c.getTable().getDatabase()).append(Constant.SqlSymbol.POINT)
                .append(c.getTable().getTableName()).append(Constant.SqlSymbol.POINT)
                .append(c.getColumn()).append(","));
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" from ").append(reportObject.getPrimaryTable().getDatabase())
            .append(Constant.SqlSymbol.POINT).append(reportObject.getPrimaryTable().getTableName());
        reportObject.getJoinTables().forEach(j->
            sql.append(Constant.SqlSymbol.SPACE).append(j.getJoinType()).append(Constant.SqlSymbol.SPACE)
                .append(j.getDatabase()).append(Constant.SqlSymbol.POINT).append(j.getTableName())
                .append(Constant.SqlSymbol.ON).append(reportObject.getPrimaryTable().getDatabase())
                .append(Constant.SqlSymbol.POINT).append(reportObject.getPrimaryTable().getTableName())
                .append(Constant.SqlSymbol.POINT).append(j.getPrimaryColumn()).append(" = ")
                .append(j.getDatabase()).append(Constant.SqlSymbol.POINT).append(j.getTableName())
                .append(Constant.SqlSymbol.POINT).append(j.getOwnColumn())
        );
        sql.append(" where 1=1");
        if (null!=reportObject.getShareRules() && !reportObject.getShareRules().isEmpty()){
            sql.append(Constant.SqlSymbol.AND).append(Constant.SqlSymbol.OPEN_PARENTHESIS);
            reportObject.getShareRules().forEach(s-> sql.append(makeConditionSql(s,reportObject.getPrimaryTable().getDatabase()))
                    .append(Constant.SqlSymbol.OR));
            //除去最后的or拼接
            sql.delete((sql.length() - Constant.SqlSymbol.OR.length()),sql.length());
            sql.append(Constant.SqlSymbol.CLOSE_PARENTHESIS);
        }
        if (null!=reportObject.getFilterRules()){
            sql.append(Constant.SqlSymbol.AND);
            sql.append(makeConditionSql(reportObject.getFilterRules(),reportObject.getPrimaryTable().getDatabase()));
        }
        return sql.toString();
    }

    /**
     * 装配查询条件
     * @param s
     * @param database
     * @return
     */
        public static String makeConditionSql(DataShareRuleDO s, String database) {
        StringBuilder sb = new StringBuilder();
        List<String> strList = s.getConditions().stream().map(r->{
            String dbname = StringUtils.hasText(r.getDatabase())? r.getDatabase(): database;
            String cond = dbname +
                    Constant.SqlSymbol.POINT + r.getTableName() +
                    Constant.SqlSymbol.POINT + r.getFieldName() +
                    Constant.SqlSymbol.SPACE + r.getOperator() + Constant.SqlSymbol.SPACE;
            return cond + fieldHandler(r.getOperator(),r.getFieldValue(),r.getFieldName(),r.getTableName());
        }).collect(Collectors.toList());
        if (StringUtils.hasText(s.getFilter())){
            ForEachUtil.forEach(0,strList,(i,f)->
                    s.setFilter(s.getFilter().replace(Constant.Symbol.NUMBER_SIGN+(i+1),f))
            );
            sb.append(s.getFilter());
        }else{
            strList.forEach(sb::append);
        }
        return sb.toString();
    }

    /**
     * 字段值处理
     * @param operator
     * @param value
     * @return
     */
    private static String fieldHandler(String operator,String value,String fieldName,String tableName){
        String result = "";
        switch (operator) {
            //以什么开始
            case "start with":
                result = Constant.SqlSymbol.SINGLE_QUOTES + value + Constant.SqlSymbol.SQL_LIKE + Constant.SqlSymbol.SINGLE_QUOTES;
                break;
            //类似/不类似
            case "like":
            case "not like":
                result = Constant.SqlSymbol.SINGLE_QUOTES + Constant.SqlSymbol.SQL_LIKE + value + Constant.SqlSymbol.SQL_LIKE + Constant.SqlSymbol.SINGLE_QUOTES;
                break;
            case "in":
                StringBuilder sb = new StringBuilder();
                Arrays.stream(value.split(Constant.Symbol.COMMA)).forEach(s ->
                        sb.append(Constant.SqlSymbol.SINGLE_QUOTES).append(s).append(Constant.SqlSymbol.SINGLE_QUOTES).append(Constant.Symbol.COMMA));
                sb.deleteCharAt(sb.length()-1);
                result = Constant.SqlSymbol.OPEN_PARENTHESIS + sb.toString() + Constant.SqlSymbol.CLOSE_PARENTHESIS;
                break;
            //为空
            case "not null":
            case "null":
                break;
            default:
                if (fieldName.equals("amount") && tableName.equals("opportunity")) {
                    result = value;
                } else {
                    result = Constant.SqlSymbol.SINGLE_QUOTES + value + Constant.SqlSymbol.SINGLE_QUOTES;
                }

                break;
        }
        return result;
    }


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

    public static DataShareRuleDO assembleConditions(Set<ColumnDO> columnSet, List<DataConditionDO> condition, ReportItemDO ReportItemDO) {
        if(!StringUtils.isEmpty(ReportItemDO.getFilterLogic())){
            return assembleFilterLogicConditions(columnSet, condition, ReportItemDO);
        }
        if (!CollectionUtils.isEmpty(ReportItemDO.getConditions())) {
            Map<String, ConditionConfigDO> map = ReportItemDO.getConditions().stream().collect(Collectors.toMap(ConditionConfigDO::getFieldName, val -> val, (v1, v2) -> v1));
            ArrayList<String> keys = new ArrayList<>(map.keySet());
            columnSet.forEach(column -> {
                if (keys.contains(column.getColumn())) {
                    ConditionConfigDO configDTO = map.get(column.getColumn());
                    condition.add(new DataConditionDO(column.getTable().getDatabase(), column.getTable().getTableName(), configDTO.getFieldName(), null, configDTO.getOperatorName(), configDTO.getFieldValue()));
                }
            });
        }
        DataShareRuleDO queryRules = new DataShareRuleDO();
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

    private static DataShareRuleDO assembleFilterLogicConditions(Set<ColumnDO> columnSet, List<DataConditionDO> condition, ReportItemDO ReportItemDO) {
        DataShareRuleDO queryRules = new DataShareRuleDO();
        List<DataConditionDO> conditions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ReportItemDO.getConditions())) {
            ReportItemDO.getConditions().forEach(c ->{
                columnSet.forEach(column -> {
                    if (column.getColumn().equals(c.getFieldName())) {
                        conditions.add(new DataConditionDO(column.getTable().getDatabase(), column.getTable().getTableName(),  c.getFieldName(), null, c.getOperatorName(), c.getFieldValue()));
                        return;
                    }
                });
            });
        }

        if (!CollectionUtils.isEmpty(condition)) {
            StringBuilder builder = new StringBuilder(ReportItemDO.getFilterLogic());
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
