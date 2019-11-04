package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.model.dto.RoleDTO;

/**
 * @auther jarod.jin 2019/11/4
 */
public interface IRoleService {

    RoleDTO saveRole(RoleDTO dto);
}
