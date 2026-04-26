package com.credentialvault.presentation.controller;

import com.credentialvault.application.service.UserAccountService;
import com.credentialvault.application.dto.user.CreateUserAccount;
import com.credentialvault.application.dto.user.ResponseUserAccount;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountController {

    @Autowired
    private UserAccountService service;

    @PostMapping
    public ResponseEntity<ResponseUserAccount> createUserAccount(@RequestBody @Valid CreateUserAccount createUserAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUserAccount(createUserAccount));
    }

}
