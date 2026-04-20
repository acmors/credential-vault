package com.credentialvault.web.controller;

import com.credentialvault.auth.AuthenticationService;
import com.credentialvault.web.dto.login.LoginRequest;
import com.credentialvault.web.dto.login.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthenticationService authenticationService;

    @PostMapping
    private ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }
}
