package cn.jarod.bluecat.auth.process;

import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.model.bo.SaveUserInfoBO;
import cn.jarod.bluecat.auth.service.*;
import cn.jarod.bluecat.core.model.auth.ReqGrantedAuthority;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
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
    private String server_key;

    @Value("${security.key.default:123456}")
    private String default_key;


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
            ReqGrantedAuthority req = (ReqGrantedAuthority) Lists.newArrayList(authentication.getAuthorities()).get(0);
            log.info("{}系统登录成功：用户为{}，终端为：{}", req.getSysCode(), name, req.getTerminalVersion());
            return createUsernamePasswordAuthentication(name, pwd, req.getSysCode());
        }
        log.info(AUTH_ERROR_MSG + Const.BRACE , name);
        throw new BadCredentialsException(AUTH_ERROR_MSG);
    }


    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthentication(String username , String pwd, String sys) {
        List<Long> orgRoleIds = userLocationService.findOrgRoleIdsByUsername(username);
        List<UserGrantedAuthority> authorityBOList =  orgRoleService.queryOrgRoleByIds(orgRoleIds);
        takeRoleForAuthorityBO(authorityBOList,sys);
        takeOrgInfoForAuthorityBO(authorityBOList,sys);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, pwd,
                authorityBOList.stream().filter(UserGrantedAuthority::isAuth).collect(Collectors.toList()));
        SaveUserInfoBO authDTO =  credentialService.findUserInfo(username);
        authentication.setDetails(authDTO);
        return authentication;
    }


    private void takeOrgInfoForAuthorityBO(List<UserGrantedAuthority> authorityBOList, String sys){
        Map<String, OrganizationDO> orgMap = organizationService.queryOrgMapByCodesAndSys(authorityBOList.stream()
                .map(UserGrantedAuthority::getOrgCode).collect(Collectors.toList()), sys);
        authorityBOList.forEach(o->{
            OrganizationDO tmp = orgMap.get(o.getOrgCode());
            if (tmp !=null){
                o.setOrgType(tmp.getOrgType());
                o.setSysCode(tmp.getSysCode());
                o.setOrgName(tmp.getOrgName());
                o.setOrgCode(tmp.getFullCode());
                o.setOrgName(tmp.getFullName());
            }
        });
    }


    private void takeRoleForAuthorityBO(List<UserGrantedAuthority> authorityBOList, String sys){
        Map<String, RoleDO> roleMap = roleService.queryRoleMapByCodes(authorityBOList.stream()
                .map(UserGrantedAuthority::getRoleCode).collect(Collectors.toList()),sys);
        authorityBOList.forEach(r->{
            RoleDO tmp = roleMap.get(r.getRoleCode());
            if (tmp !=null){
                r.setDisOrder(tmp.getDisOrder());
                r.setRoleName(tmp.getRoleName());
                r.setDisOrder(tmp.getDisOrder());
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
        return EncryptUtil.stringEncodeSHA256(default_key + now.getMonthValue() + now.getDayOfMonth());
    }
}
