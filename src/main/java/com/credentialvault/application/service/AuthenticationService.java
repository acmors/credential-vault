package com.credentialvault.application.service;

import com.credentialvault.domain.model.UserAccount;
import com.credentialvault.common.exceptions.business.BadRequestException;
import com.credentialvault.infra.security.jwt.JwtService;
import com.credentialvault.application.dto.login.LoginRequest;
import com.credentialvault.application.dto.login.LoginResponse;
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
            throw new BadRequestException("wrong password");
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
