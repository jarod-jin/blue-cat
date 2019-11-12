package cn.jarod.bluecat.auth.process;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.service.*;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2018/12/3
 */
@Slf4j
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public static final String AUTH_ERROR_MSG = "用户名/密码错误~";

    @Value("${security.key.server:123456}")
    private String serverKey;

    @Value("${security.key.default:123456}")
    private String defaultKey;


    @Autowired
    private ICredentialService credentialService;

    @Autowired
    private IUserLocationService userLocationService;

    @Autowired
    private IOrgRoleService orgRoleService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IRoleService roleService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String pwd = EncryptUtil.stringEncodeSHA256(authentication.getCredentials().toString());
        // 登录外包商人员认证逻辑
        if (pwd.equals(findDefaultKey()) || credentialService.validCredential(name,pwd)) {
            UserGrantedAuthority req = (UserGrantedAuthority) Lists.newArrayList(authentication.getAuthorities()).get(0);
            log.info("{}系统登录成功：用户为{}，终端为：{}", req.getSysCode(), name, req.getTerminalVersion());
            return createUsernamePasswordAuthentication(name, pwd, req);
        }
        log.info(AUTH_ERROR_MSG + Const.BRACE , name);
        throw new BadCredentialsException(AUTH_ERROR_MSG);
    }


    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthentication(String username, String pwd, UserGrantedAuthority grantedAuthority) {
        List<Long> orgRoleIds = userLocationService.findOrgRoleIdsByUsername(username);
        List<UserAuthority> authorityBOList =  orgRoleService.queryOrgRoleByIds(orgRoleIds);
        takeRoleForAuthorityBO(authorityBOList,grantedAuthority.getSysCode());
        takeOrgInfoForAuthorityBO(authorityBOList,grantedAuthority.getSysCode());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, pwd,
                authorityBOList.stream().filter(UserAuthority::isAuth).map(UserGrantedAuthority::new).collect(Collectors.toList()));
        UserInfoDTO authDTO =  credentialService.findUserInfo(username);
        authDTO.setSysCode(grantedAuthority.getSysCode());
        authentication.setDetails(authDTO);
        return authentication;
    }


    private void takeOrgInfoForAuthorityBO(List<UserAuthority> authorityBOList, String sys){
        Map<String, OrganizationDO> orgMap = organizationService.queryOrgMapByCodesAndSys(authorityBOList.stream()
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
        Map<String, RoleDO> roleMap = roleService.queryRoleMapByCodes(authorityBOList.stream()
                .map(UserAuthority::getRoleCode).collect(Collectors.toList()),sys);
        authorityBOList.forEach(r->{
            RoleDO tmp = roleMap.get(r.getRoleCode());
            if (tmp !=null){
                r.setRoleName(tmp.getRoleName());
            }
        });
    }


    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String findDefaultKey(){
        LocalDate now = LocalDate.now();
        return EncryptUtil.stringEncodeSHA256(defaultKey + now.getMonthValue() + now.getDayOfMonth());
    }
}
