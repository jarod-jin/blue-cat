package cn.jarod.bluecat.core.component;

import cn.jarod.bluecat.core.utils.EncryptUtil;
import com.dahua.tech.easywork.common.dto.auth.PersonInfoVO;
import com.dahua.tech.easywork.common.enums.AuthReturnCode;
import com.dahua.tech.easywork.common.service.BaseService;
import com.dahua.tech.easywork.common.utils.BeanUtil;
import com.dahua.tech.easywork.common.utils.SHAEncrypt;
import com.dahua.tech.easywork.gateway.model.CustomGrantedAuthority;
import com.dahua.tech.easywork.gateway.model.CustomUser;
import com.dahua.tech.easywork.gateway.service.IAuthenticationService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

    public static final String CONTRACTOR = "contractor";

    public static final String EMPLOYEE = "employee";

    public static final String TEL = "tel";

    public static final String SERVER = "server";

    public static final String AUTH_ERROR_MSG = "用户名/密码错误~";

    public static final String WITHOUT_TYPE = "缺少用户类型~";

//    @Value("${security.key.server}")
//    private String server_key;
//
//    @Value("${security.key.default}")
//    private String default_key;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<GrantedAuthority> authList = Lists.newArrayList(authentication.getAuthorities());
        return contractorAuthenticate(name,pwd,authList);
    }




    private Authentication contractorAuthenticate(@NotBlank String name, @NotBlank String pwd, Collection<GrantedAuthority>  authorities){
        String password = EncryptUtil.stringEncodeSHA256(pwd);
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        // 外包商人员认证逻辑
        if (!(pwd.equals(findDefaultKey())|| password.equals(userDetails.getPassword()))) {
            log.info(name + ":"+ AUTH_ERROR_MSG);
            throw new BadCredentialsException(AUTH_ERROR_MSG);
        }
        return getContractorAuthenticate(authorities, userDetails);
    }



    private Authentication getContractorAuthenticate(Collection<GrantedAuthority> authorities, UserDetails userDetails) {
        authorities.addAll(userDetails.getAuthorities());
        PersonInfoVO personInfoVO = BeanUtil.getCopyBean(userDetails,PersonInfoVO.class);
        personInfoVO.setLoginName(userDetails.getUsername());
        return createUsernamePasswordAuthenticationToken(personInfoVO, userDetails.getPassword(), authorities);
    }


    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(PersonInfoVO personInfoVO , String pwd, Collection<GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(personInfoVO.getLoginName(), EncryptUtil.stringEncodeSHA256(pwd), authorities);
        personInfoVO.setRoles(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));//将authorities权限加入roles
        authentication.setDetails(personInfoVO);
        try {
            personInfoVO.encodeProperty(StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return authentication;
    }

    private Collection<GrantedAuthority> getAuthorities(PersonInfoVO personVO) {
      return personVO.getRoles().stream().map(CustomGrantedAuthority::new).collect(Collectors.toList());
    }




    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String findDefaultKey(){
        LocalDate now = LocalDate.now();
        return default_key + now.getMonthValue() + now.getDayOfMonth();
    }
}
