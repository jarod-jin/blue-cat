package cn.jarod.bluecat.core.log.pojo;

import cn.jarod.bluecat.core.common.utils.JsonUtil;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */

@Data
@AllArgsConstructor
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
    private Map<String, String[]> params;

    /**
     *是否操作成功
     */
    private Boolean flag;

    /**
     *操作时间
     */
    private LocalDateTime operatorTime;

    public OperationLogDTO(){
        operatorTime = LocalDateTime.now();
        params= Maps.newHashMap();
    }

    @Override
    public String toString(){
        HashMap<String, Object> logMap = new HashMap<>(8);
        logMap.put("operatorTime",this.getOperatorTime());
        logMap.put("ip",this.getIp());
        logMap.put("description",this.getDescription());
        logMap.put("operation",this.getOperationType());
        logMap.put("operator",this.getOperator());
//        logMap.put("result", null == httpServletRequest.getAttribute("flag"));
        return JsonUtil.toJson(this);
    }


}
