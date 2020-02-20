package cn.jarod.bluecat.auth.procedure;

import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.entity.OrganizationDO;
import cn.jarod.bluecat.auth.entity.RoleDO;
import cn.jarod.bluecat.auth.enums.SignType;
import cn.jarod.bluecat.auth.model.bo.CrudUserBO;
import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.service.CredentialService;
import cn.jarod.bluecat.auth.service.OrganizationService;
import cn.jarod.bluecat.auth.service.RoleService;
import cn.jarod.bluecat.auth.service.UserLocationService;
import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.component.SecurityPropertyConfiguration;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.AuthCredentials;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.CommonUtil;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import cn.jarod.bluecat.core.utils.TokenAuthenticationUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/9/10
 */
@Slf4j
@Service
public class UserAuthenticationHandler {

    private static final String AUTH_ERROR_MSG = "用户名/密码错误~";

    private final CredentialService credentialService;

    private final UserLocationService userLocationService;

    private final OrganizationService organizationService;

    private final RoleService roleService;

    private SecurityPropertyConfiguration securityConfig;

    public UserAuthenticationHandler(CredentialService credentialService,
                                     UserLocationService userLocationService,
                                     OrganizationService organizationService,
                                     SecurityPropertyConfiguration securityConfig,
                                     RoleService roleService) {
        this.credentialService = credentialService;
        this.userLocationService = userLocationService;
        this.organizationService = organizationService;
        this.securityConfig = securityConfig;
        this.roleService = roleService;
    }

    /**
     * 注册前校验
     * @return ResultDTO
     */
    public ResultDTO validAuthority(String typeStr,String text) {
        return ApiResultUtil.getSuccess(credentialService.validSignUp(SignType.findSignType(typeStr),text));
    }


    /**
     * 注册用户
     * @param upDTO 新用户
     * @return ResultDTO
     */
    public ResultDTO signUp(SignUpDTO upDTO) {
        CrudUserBO crudUserBO = new CrudUserBO();
        if (upDTO.getSignType()==0){
            if (!CommonUtil.validTel(upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"请输入一个正确的手机号"); }
            if (!credentialService.validSignUp(SignType.tel,upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"该手机已被注册"); }
            crudUserBO.setTel(upDTO.getSignName());
        }else if (upDTO.getSignType()==1){
            if (!CommonUtil.validEmail(upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"请输入一个正确的邮箱地址"); }
            if (!credentialService.validSignUp(SignType.email,upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"该邮箱已被注册"); }
            crudUserBO.setEmail(upDTO.getSignName());
        }else{
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE);
        }
        String username = credentialService.bookUsername(crudUserBO);
        crudUserBO.setUsername(username);
        credentialService.setSignInfo2Redis(upDTO.getSignName());
        credentialService.setSignInfo2Redis(username);
        crudUserBO.setCredentialType(upDTO.getCredentialType());
        return ApiResultUtil.getSuccess(credentialService.registerUser(crudUserBO,upDTO.getPassword()));
    }

    public ResultDTO refreshAccessToken(HttpServletRequest request) {
        String token = request.getHeader(Constant.Common.ACCESS_TOKEN);
        return  ApiResultUtil.getSuccess(credentialService.refresh(token));
    }

    public ResultDTO signIn(AuthCredentials credentials) {
        /*获取认证的用户名 & 密码*/
        String name = credentials.getSignIn();
        Optional<CredentialDO> credOpt = credentialService.findCredentialByUsername(name);
        if (credOpt.isPresent() && validPassword(String.valueOf(credentials.getPassword()),credOpt.get())) {
            UserGrantedAuthority req = new UserGrantedAuthority(credentials.getBelongTo(), credentials.getTerminalVersion());
            log.info("{}系统登录成功：用户为{}，终端为：{}", req.getBelongTo(), name, req.getTerminalVersion());
            return TokenAuthenticationUtil.addAuthentication(createUsernamePasswordAuthentication(name, credOpt.get().getPassword(), req),securityConfig);
        }
        log.warn(AUTH_ERROR_MSG + Constant.Symbol.BRACE , name);
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

}
