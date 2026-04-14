package com.credentialvault.web.controller;

import com.credentialvault.service.CredentialService;
import com.credentialvault.web.dto.credential.CreateCredential;
import com.credentialvault.web.dto.credential.ResponseCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credential")
public class CredentialController {

    @Autowired
    private CredentialService service;

    @PostMapping
    public ResponseEntity<ResponseCredential> createCredential(@RequestBody CreateCredential dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCredential(dto));
    }

    @GetMapping
    public ResponseEntity<List<ResponseCredential>> findAll(){
        return ResponseEntity.ok().body(service.findAllCredentials());
    }
}
