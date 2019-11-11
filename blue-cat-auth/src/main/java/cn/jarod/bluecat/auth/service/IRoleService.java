package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.SaveRoleBO;
import cn.jarod.bluecat.core.model.BaseQO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @auther jarod.jin 2019/11/4
 */
public interface IRoleService {

    RoleDO saveRole(SaveRoleBO dto);

    void delRole(SaveRoleBO dto);

    Map<String,RoleDO> queryRoleMapByCodes(List<String> codes);

    Page<RoleDO> queryRolePage(BaseQO qo);
}

