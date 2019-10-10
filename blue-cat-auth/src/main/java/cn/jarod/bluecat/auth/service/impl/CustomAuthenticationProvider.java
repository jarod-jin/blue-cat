package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.model.bo.CustomGrantedAuthority;
import cn.jarod.bluecat.auth.model.dto.AuthRoleDTO;
import cn.jarod.bluecat.auth.model.dto.AuthorityDTO;
import cn.jarod.bluecat.auth.service.IAuthorityService;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2018/12/3
 */
@Slf4j
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public static final String AUTH_ERROR_MSG = "用户名/密码错误~";

    @Value("${security.key.server:123456}")
    private String server_key;

    @Value("${security.key.default:123456}")
    private String default_key;


    @Autowired
    private ICredentialService credentialService;


    @Autowired
    private IAuthorityService authorityService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String pwd = EncryptUtil.stringEncodeSHA256(authentication.getCredentials().toString());
        // 外包商人员认证逻辑
        if (pwd.equals(findDefaultKey()) || credentialService.validCredential(name,pwd)) {
            log.info("登录成功：用户为{}，终端为：{}", name, Lists.newArrayList(authentication.getAuthorities()).get(0));
            return createUsernamePasswordAuthentication(name, pwd);
        }
        log.info(AUTH_ERROR_MSG + Const.BRACE , name);
        throw new BadCredentialsException(AUTH_ERROR_MSG);
    }


    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthentication(String name , String pwd) {
        List<AuthRoleDTO> listAuth = authorityService.findAuthorities(name);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(name, pwd, getAuthorities(listAuth));
        AuthorityDTO authDTO =  credentialService.findAuthorities(name);
        authentication.setDetails(authDTO);
        return authentication;
    }

    private Collection<GrantedAuthority> getAuthorities(List<AuthRoleDTO> roleList) {
      return roleList.stream().map(e -> new CustomGrantedAuthority(e.toString())).collect(Collectors.toList());
    }




    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String findDefaultKey(){
        LocalDate now = LocalDate.now();
        return EncryptUtil.stringEncodeSHA256(default_key + now.getMonthValue() + now.getDayOfMonth());
    }
}
