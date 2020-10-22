package cn.jarod.bluecat.core.common.utils;


import cn.jarod.bluecat.core.api.util.ApiResultUtil;
import cn.jarod.bluecat.core.common.enums.CommonPattern;
import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.oauth.pojo.DataCondition;
import cn.jarod.bluecat.core.oauth.pojo.DataShareRules;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/23
 */
@Slf4j
public class ShareRuleUtil {

    public static final String STRING = "string";

    /**
     * 权限将运算式转化成后缀运算式
     *
     * @param expression 权限将运算式
     * @return String
     */
    public static String convertToPostfix(@NotBlank String expression) {
        //转化表达式成为单字符集表达式 替换AND和OR 为a和o
        expression = expression.replace("AND", "a").replace("OR", "o");
        //为了联合计算方便
        expression = "(" + expression + ")";
        //用于储存后缀表达式
        StringBuilder postfix = new StringBuilder();
        //初始化一个运算符栈
        Stack<Character> st = new Stack<>();
        for (char c : expression.toCharArray()) {
            switch (c) {
                case ' ':
                    break;
                case '(':
                    //为左括号
                    st.push(c);
                    break;
                case ')':
                    //为右括号
                    char ac = st.pop();
                    while (!isOpenParent(ac)) {
                        postfix.append(ac);
                        ac = st.pop();
                    }
                    break;
                case 'a':
                case 'o':
                    //运算栈非空，取出栈顶运算符写入后缀表达式
                    if (!st.empty()) {
                        char ad = st.pop();
                        //栈取出的字符
                        while (!st.isEmpty() && isOperator(ad)) {
                            postfix.append(ad);
                            ad = st.pop();
                        }
                        if (isNotBlank(ad)) {
                            st.push(ad);
                        }
                    }
                    //将c入栈
                    st.push(c);
                    break;
                default:
                    postfix.append(c);
            }
        }
        //当表达式读完就将算术栈pop出加入postfix
        while (!st.isEmpty()) {
            postfix.append(st.pop());
        }
        return postfix.toString();
    }

    private static boolean isNotBlank(char c) {
        return c != ' ';
    }


    /**
     * 判断字符为左括号
     *
     * @param c
     * @return
     */
    private static boolean isOpenParent(char c) {
        return c == '(';
    }

    /**
     * 判断字符为SQL运算符
     *
     * @param c
     * @return
     */
    private static boolean isOperator(char c) {
        return c == 'a' || c == 'o';
    }


    /**
     * 根据shareRules返回对应Predicate
     *
     * @param criteriaBuilder
     * @param root
     * @param shareRules
     * @param <T>
     * @return Predicate
     */
    public static <T> Predicate handleShareRulePredicate(@NotNull CriteriaBuilder criteriaBuilder, @NotNull Root<T> root, @NotNull DataShareRules shareRules) {
        if (StringUtils.isNotBlank(shareRules.getFilter())) {
            String orderStr = convertToPostfix(shareRules.getFilter());
            Stack<Predicate> stack = new Stack<>();
            for (char c : orderStr.toCharArray()) {
                switch (c) {
                    case 'a':
                        Predicate andPredicate = criteriaBuilder.and(stack.pop(), stack.pop());
                        stack.push(andPredicate);
                        break;
                    case 'o':
                        Predicate orPredicate = criteriaBuilder.or(stack.pop(), stack.pop());
                        stack.push(orPredicate);
                        break;
                    default:
                        DataCondition cond = shareRules.getConditions().get(Integer.parseInt(String.valueOf(c)) - 1);
                        stack.push(handleCondition(criteriaBuilder, root, cond));
                }
            }
            return stack.pop();
        }
        return handleCondition(criteriaBuilder, root, shareRules.getConditions().get(0));
    }


    /**
     * 返回单个Predicate
     *
     * @param criteriaBuilder
     * @param root
     * @param cond
     * @param <T>
     * @return
     */
    public static <T> Predicate handleCondition(CriteriaBuilder criteriaBuilder, Root<T> root, DataCondition cond) {
        Predicate conditions;
        //将万恶的下划线转驼峰字段
        cond.setFieldName(BeanHelperUtil.underlineToHump(cond.getFieldName()));
        Path<Object> path = root.get(cond.getFieldName());
        CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
        switch (cond.getOperator()) {
            //等于
            case "=":
                conditions = criteriaBuilder.equal(root.get(cond.getFieldName()), stringConverter(cond.getFieldValue(),cond.getDataType()));
                break;
            //不等于
            case "!=":
                conditions = criteriaBuilder.notEqual(root.get(cond.getFieldName()), stringConverter(cond.getFieldValue(),cond.getDataType()));
                break;
            //包含
            case "in":
                conditions = takeInCriteriaBuilder(cond, in, criteriaBuilder);
                break;
            //包含
            case "not in":
                conditions = takeInCriteriaBuilder(cond, in, criteriaBuilder).not();
                break;
            //小于
            case "<":
                conditions = criteriaBuilder.lessThan(root.get(cond.getFieldName()), cond.getFieldValue());
                break;
            //小于或等于
            case "<=":
                conditions = criteriaBuilder.lessThanOrEqualTo(root.get(cond.getFieldName()), cond.getFieldValue());
                break;
            //大于
            case ">":
                conditions = criteriaBuilder.greaterThan(root.get(cond.getFieldName()), cond.getFieldValue());
                break;
            //大于或等于
            case ">=":
                conditions = criteriaBuilder.greaterThanOrEqualTo(root.get(cond.getFieldName()), cond.getFieldValue());
                break;
            //以什么开始
            case "start with":
                conditions = criteriaBuilder.like(root.get(cond.getFieldName()), cond.getFieldValue() + Constant.SqlSymbol.SQL_LIKE);
                break;
            //类似
            case "like":
                conditions = criteriaBuilder.like(root.get(cond.getFieldName()), Constant.SqlSymbol.SQL_LIKE + cond.getFieldValue() + Constant.SqlSymbol.SQL_LIKE);
                break;
            //不类似
            case "not like":
                conditions = criteriaBuilder.like(root.get(cond.getFieldName()), Constant.SqlSymbol.SQL_LIKE + cond.getFieldValue() + Constant.SqlSymbol.SQL_LIKE).not();
                break;
            //为空
            case "null":
                conditions = criteriaBuilder.isNull(root.get(cond.getFieldName()));
                break;
            //不为空
            case "not null":
                conditions = criteriaBuilder.isNotNull(root.get(cond.getFieldName()));
                break;
            default:
                conditions = null;
                break;
        }
        return conditions;
    }

    private static Predicate takeInCriteriaBuilder(DataCondition cond, CriteriaBuilder.In<Object> in, CriteriaBuilder criteriaBuilder) {
        Arrays.stream(cond.getFieldValue()
                .split(Constant.Symbol.COMMA))
                .forEach(e -> in.value(getInValue(e, cond.getDataType())));
        return criteriaBuilder.and(in);
    }

    private static Object getInValue(String e, String dataType) {
        return e.matches(CommonPattern.ONLY_DIGIT.getPattern()) && !dataType.equals(STRING) ? Long.parseLong(e) : e;
    }

    /**
     * 查询条件时的数字判断
     *
     * @param value
     * @param dataType
     * @return Object
     */
    public static Object stringConverter(String value, String dataType) {
        Object object;
        try {
            switch (dataType) {
                case "int":
                    object = Integer.parseInt(value);
                    break;
                case "long":
                    object = Long.parseLong(value);
                    break;
                case "decimal":
                    object = new BigDecimal(value);
                    break;
                case "date":
                    object = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constant.Common.DEFAULT_DATE_TIME_FORMAT));
                    break;
                case "datetime":
                    object = LocalDate.parse(value, DateTimeFormatter.ofPattern(Constant.Common.DEFAULT_DATE_FORMAT));
                    break;
                case "boolean":
                    object = Boolean.parseBoolean(value);
                    break;
                default:
                    object = value;
            }
        } catch (ClassCastException el) {
            log.warn("类型转换出错：{} 不是一个 {}", value, dataType);
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE);
        }
        return object;
    }
}
