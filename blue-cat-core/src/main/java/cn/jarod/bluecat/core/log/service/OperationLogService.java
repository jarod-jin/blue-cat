package cn.jarod.bluecat.core.log.service;

import cn.jarod.bluecat.core.log.entity.OperationLogDO;
import cn.jarod.bluecat.core.log.model.OperationLogDTO;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
public interface OperationLogService {

    /**
     * 添加log
     * @param operationLogDTO
     * @return
     */
    OperationLogDO create(OperationLogDTO operationLogDTO);
}
