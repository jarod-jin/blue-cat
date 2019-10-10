package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.model.dto.AuthRoleDTO;

import java.util.List;

/**
 * @auther jarod.jin 2019/10/10
 */
public interface IAuthorityService {

    List<AuthRoleDTO> findAuthorities(String name);
}
