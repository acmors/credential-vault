package com.credentialvault.web.controller;

import com.credentialvault.jwt.JwtUserDetailsService;
import com.credentialvault.web.dto.login.Auth;
import com.credentialvault.web.dto.login.Token;
import com.credentialvault.web.exceptions.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody Auth auth, HttpServletRequest request){
        log.info("Processo de autenticação pelo login {}", auth.getEmail());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());

            authenticationManager.authenticate(authenticationToken);

            Token token = detailsService.getTokenAuthenticated(auth.getEmail());

            return ResponseEntity.ok(token);
        } catch (RuntimeException ex) {
            log.warn("Bad Credentials from username '{}'", auth.getEmail());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
