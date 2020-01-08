package cn.jarod.bluecat.auth.procedure;

import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.service.*;
import cn.jarod.bluecat.core.constant.Symbol;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;
import cn.jarod.bluecat.core.constant.Common;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2018/12/3
 */
@Slf4j
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String AUTH_ERROR_MSG = "用户名/密码错误~";

    private final CredentialService credentialService;

    private final UserLocationService userLocationService;

    private final OrganizationService organizationService;

    private final RoleService roleService;

    public CustomAuthenticationProvider(CredentialService credentialService, UserLocationService userLocationService, OrganizationService organizationService, RoleService roleService) {
        this.credentialService = credentialService;
        this.userLocationService = userLocationService;
        this.organizationService = organizationService;
        this.roleService = roleService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /*获取认证的用户名 & 密码*/
        String name = authentication.getName();
        Optional<CredentialDO> credOpt = credentialService.findCredentialByUsername(name);
        if (credOpt.isPresent() && validPassword(String.valueOf(authentication.getCredentials()),credOpt.get())) {
            UserGrantedAuthority req = (UserGrantedAuthority) Lists.newArrayList(authentication.getAuthorities()).get(0);
            log.info("{}系统登录成功：用户为{}，终端为：{}", req.getBelongTo(), name, req.getTerminalVersion());
            return createUsernamePasswordAuthentication(name, credOpt.get().getPassword(), req);
        }
        log.info(AUTH_ERROR_MSG + Symbol.BRACE , name);
        throw new BadCredentialsException(AUTH_ERROR_MSG);
    }

    /**
     * 密码校验
     * @param targetPwd 目标密码
     * @param credDO 密码保存对象
     * @return boolean
     */
    private boolean validPassword(String targetPwd, CredentialDO credDO){
        return EncryptUtil.encodePassword(targetPwd,credDO.getSalt()).equals(credDO.getPassword());
    }




    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthentication(String username, String pwd, UserGrantedAuthority grantedAuthority) {
        List<Long> orgRoleIds = userLocationService.findOrgRoleIdsByUsername(username);
        List<UserAuthority> authorityBOList =  roleService.findOrgRoleByIds(orgRoleIds);
        takeRoleForAuthorityBO(authorityBOList,grantedAuthority.getBelongTo());
        takeOrgInfoForAuthorityBO(authorityBOList,grantedAuthority.getBelongTo());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, pwd,
                authorityBOList.stream()
                        .filter(UserAuthority::isAuth)
                        .map(UserAuthority::getRoleCode)
                        .distinct()
                        .map(UserGrantedAuthority::new)
                        .collect(Collectors.toList()));
        UserInfoDTO authDTO =  credentialService.findUserInfo(username);
        authDTO.setBelongTo(grantedAuthority.getBelongTo());
        authDTO.setTerminalVersion(grantedAuthority.getTerminalVersion());
        authDTO.setAuthorityList(authorityBOList);
        if (credentialService.setUserInfo2Cache(authDTO)){
            return authentication;
        }
        throw new BadCredentialsException(AUTH_ERROR_MSG);
    }



    private void takeOrgInfoForAuthorityBO(List<UserAuthority> authorityBOList, String sys){
        Map<String, OrganizationDO> orgMap = organizationService.findOrgMapByCodesAndSys(authorityBOList.stream()
                .map(UserAuthority::getOrgCode).collect(Collectors.toList()), sys);
        authorityBOList.forEach(o->{
            OrganizationDO tmp = orgMap.get(o.getOrgCode());
            if (tmp !=null){
                o.setOrgType(tmp.getOrgType());
                o.setOrgName(tmp.getOrgName());
                o.setOrgCode(tmp.getFullCode());
                o.setOrgName(tmp.getFullName());
            }
        });
    }


    private void takeRoleForAuthorityBO(List<UserAuthority> authorityBOList, String sys){
        Map<String, RoleDO> roleMap = roleService.findRoleMapByCodes(authorityBOList.stream()
                .map(UserAuthority::getRoleCode).collect(Collectors.toList()),sys);
        authorityBOList.forEach(r->{
            RoleDO tmp = roleMap.get(r.getRoleCode());
            if (tmp !=null){
                r.setRoleName(tmp.getRoleName());
            }
        });
    }


    /**是否可以提供输入类型的认证服务*/
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
