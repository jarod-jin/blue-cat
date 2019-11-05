package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.service.IAuthorityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther jarod.jin 2019/11/5
 */
class AuthorityServiceTest extends BlueCatAuthApplicationTest {

    @Autowired
    private IAuthorityService authorityService;

    @BeforeEach
    void setUp()  {

    }

    @AfterEach
    void tearDown()  {

    }

    @Test
    void findAuthorities() {
    }

    @Test
    void saveAuthorities() {
    }

    @Test
    void saveOrgRole() {
    }
}