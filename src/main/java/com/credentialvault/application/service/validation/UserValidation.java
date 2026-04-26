package com.credentialvault.application.service.validation;

import com.credentialvault.common.exceptions.business.EmailAlreadyExistsException;
import com.credentialvault.infra.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserValidation {

    private final UserAccountRepository repository;

    public void existsByEmail(String email){
        if(repository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email already exists.");
        }
    }
}
