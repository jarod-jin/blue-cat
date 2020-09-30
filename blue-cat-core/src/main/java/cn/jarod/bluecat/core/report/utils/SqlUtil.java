package cn.jarod.bluecat.report.utils;


import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.model.auth.ShareRulesDTO;
import cn.jarod.bluecat.core.utils.ForEachUtil;
import cn.jarod.bluecat.report.pojo.ReportObjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SqlUtil {

    /**
     * 生成报表sql
     * @param reportObject
     * @return
     */
    public static String sqlHandler(ReportObjectDTO reportObject){
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
        public static String makeConditionSql(ShareRulesDTO s,String database) {
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
}
