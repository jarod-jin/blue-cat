package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.model.dto.AuthRoleDTO;
import cn.jarod.bluecat.auth.service.IAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther jarod.jin 2019/10/10
 */
@Slf4j
@Service
public class AuthorityService implements IAuthorityService {

    public List<AuthRoleDTO> findAuthorities(String name) {
        return Lists.newArrayList();
    }
}
