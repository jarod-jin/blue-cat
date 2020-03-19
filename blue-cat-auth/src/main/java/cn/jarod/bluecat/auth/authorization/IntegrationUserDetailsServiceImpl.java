package cn.jarod.bluecat.auth.authorization;



import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Jarod Jin E-mail:jin_yibing@dahuatech.com
 * @version 创建时间：2020/3/4
 */
@Slf4j
@Service("integrationUserDetailsService")
public class IntegrationUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public IntegrationUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userService.findByUserCode(username);

        UserDetailDTO userDetail = new UserDetailDTO();
        userDetail.setUserCode(userDO.getUserCode());
        userDetail.setEmail(userDO.getEmail());
        userDetail.setNickname(userDO.getNickname());
        userDetail.setTel(userDO.getTel());
        userDetail.setUserId(userDO.getId());
        /*
         * 用户权限列表。这里是为了方便模拟，实际应该从权限表中查询用户的权限列表
         */
        userDetail.setAccountNonExpired(true);
        userDetail.setAccountNonLocked(true);
        userDetail.setCredentialsNonExpired(true);
        userDetail.setEnabled(true);
        setAuthorize(userDetail);
        return userDetail;
    }

    /**
     * 设置授权信息
     *
     * @param user
     */
    public void setAuthorize(UserDetailDTO user) {
        //todo  权限设定
        user.setGrantedAuthorities(Lists.newArrayList(new SimpleGrantedAuthority("all")));
    }
}
