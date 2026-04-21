package com.credentialvault.service;

import com.credentialvault.domain.UserAccount;
import com.credentialvault.jwt.JwtService;
import com.credentialvault.web.dto.login.LoginRequest;
import com.credentialvault.web.dto.login.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserAccountService accountService;
    private final PasswordEncoder encoder;


    public LoginResponse authenticate(LoginRequest login){

        UserAccount user = accountService.findByEmailEntity(login.getEmail());

        if (!encoder.matches(login.getPassword(), user.getPassword())){
            throw new RuntimeException("wrong password");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );

        String token = jwtService.generateToken(authentication);
        return new LoginResponse(token);
    }
}
