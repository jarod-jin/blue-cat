package cn.jarod.bluecat.core.model;

import com.google.common.collect.Maps;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */

@Data
public class OperationLogDTO {

    /**
     *操作员
     */
    private String operator;

    /**
     *操作员名字
     */
    private String operatorName;

    /**
     *客户端ip
     */
    private String ip;

    /**
     *操作类型
     */
    private String operationType;

    /**
     *操作描述
     */
    private String description;

    /**
     *传入参数
     */
    private Map<String, String[]> paramsMap;

    /**
     *是否操作成功
     */
    private Boolean flag;

    /**
     *操作时间
     */
    private ZonedDateTime operatorTime;

    public OperationLogDTO(){
        operatorTime = ZonedDateTime.now();
        paramsMap = Maps.newHashMap();
    }


}
