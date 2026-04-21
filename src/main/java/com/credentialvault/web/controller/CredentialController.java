package com.credentialvault.web.controller;
import com.credentialvault.service.CredentialService;
import com.credentialvault.web.dto.credential.CreateCredential;
import com.credentialvault.web.dto.credential.ResponseCredential;
import com.credentialvault.web.dto.credential.UpdateCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credential")
public class CredentialController {

    @Autowired
    private CredentialService service;

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
    public ResponseEntity<List<ResponseCredential>> getMyCredentials(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        return ResponseEntity.ok(service.findAllCredentialsByUser(email));
    }
}
