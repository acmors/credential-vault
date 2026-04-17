package com.credentialvault.jwt;

import com.credentialvault.domain.UserAccount;
import com.credentialvault.jwt.JwtUserDetails;
import com.credentialvault.jwt.JwtUtils;
import com.credentialvault.service.UserAccountService;

import com.credentialvault.web.dto.login.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserAccountService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userService.findByEmailEntity(username);
        return new JwtUserDetails(userAccount);
    }

    public Token getTokenAuthenticated(String username) {
        UserAccount.Role role = userService.findRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
