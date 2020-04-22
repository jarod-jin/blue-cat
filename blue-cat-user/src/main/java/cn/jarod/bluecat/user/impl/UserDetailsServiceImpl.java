package cn.jarod.bluecat.user.impl;

import cn.jarod.bluecat.user.entity.CredentialDO;
import cn.jarod.bluecat.auth.model.bo.SignInCredentialBO;
import cn.jarod.bluecat.user.repository.CredentialRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/3
 */
@Service("CustomUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    public UserDetailsServiceImpl(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CredentialDO credentialDO = credentialRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("用户不存在"));

        /*
         * 用户权限列表。这里是为了方便模拟，实际应该从权限表中查询用户的权限列表
         */
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("all");

        return new SignInCredentialBO(
                credentialDO.getUsername(),
                credentialDO.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
