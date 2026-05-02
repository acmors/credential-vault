package com.credentialvault.presentation.controller;
import com.credentialvault.application.service.CredentialService;
import com.credentialvault.application.dto.credential.CreateCredential;
import com.credentialvault.application.dto.credential.ResponseCredential;
import com.credentialvault.application.dto.credential.UpdateCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credential")
@RequiredArgsConstructor
public class CredentialController {


    private final CredentialService service;

    @PostMapping
    public ResponseEntity<ResponseCredential> createCredential(@RequestBody CreateCredential dto, @AuthenticationPrincipal Jwt jwt){
        String email = jwt.getSubject();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCredential(dto, email));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResponseCredential> updateCredential(@PathVariable Long id, @RequestBody UpdateCredential update, @AuthenticationPrincipal Jwt jwt){
        String email = jwt.getSubject();
        return ResponseEntity.ok().body(service.updateCredential(id, update, email));
    }

    @GetMapping("/credentials")
    public ResponseEntity<Page<ResponseCredential>> getMyCredentials(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        String email = jwt.getSubject();
        return ResponseEntity.ok(service.findAllCredentialsByUser(email, pageable));
    }
}
