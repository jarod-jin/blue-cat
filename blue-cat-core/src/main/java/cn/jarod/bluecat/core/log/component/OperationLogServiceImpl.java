package cn.jarod.bluecat.core.log.component;

import cn.jarod.bluecat.core.log.pojo.OperationLogDO;
import cn.jarod.bluecat.core.log.pojo.OperationLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
@Slf4j
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Override
    public OperationLogDO create(OperationLogDTO operationLogDTO) {
        return null;
    }
}
