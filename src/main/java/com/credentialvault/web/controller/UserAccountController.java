package com.credentialvault.web.controller;

import com.credentialvault.service.UserAccountService;
import com.credentialvault.web.dto.user.CreateUserAccount;
import com.credentialvault.web.dto.user.ResponseUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountController {

    @Autowired
    private UserAccountService service;

    @PostMapping
    public ResponseEntity<ResponseUserAccount> createUserAccount(@RequestBody CreateUserAccount createUserAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUserAccount(createUserAccount));
    }

}
