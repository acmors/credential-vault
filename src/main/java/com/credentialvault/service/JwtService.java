package com.credentialvault.service;

import com.credentialvault.domain.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final Integer DAY_IN_HOURS = 24;

    @Value("${app.token.secret-key}")
    private String secretKey;

    public String createToken(UserAccount user){
        var data = new HashMap<String, String>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());

        return Jwts
                .builder()
                .claims(data)
                .expiration(generateExpirestAt())
                .signWith(generateSign())
                .compact();
    }

    public void validateAcessToken(String token) {
        var accessToken = extractToken(token);

        try {
            Jwts
                    .parser()
                    .verifyWith(generateSign())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        }catch (Exception e){
            throw new RuntimeException("Invalid Token." + e.getMessage());
        }
    }

    public SecretKey generateSign(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Date generateExpirestAt(){
        return Date.from(
                LocalDateTime.now()
                        .plusHours(DAY_IN_HOURS)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    private String extractToken(String token){
        if (ObjectUtils.isEmpty(token)){
            throw new RuntimeException("object is null.");
        }

        if (token.contains(EMPTY_SPACE)){
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }

        return token;
    }
}
