package com.credentialvault.application.service.validation;

import com.credentialvault.common.exceptions.business.AccessDeniedException;
import com.credentialvault.domain.model.Credential;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CredentialValidations {

    public void validateOwner(Credential credential, String email){
        if(credential.getUser() == null || !credential.getUser().getEmail().equals(email)){
            throw new AccessDeniedException("Access denied.");
        }
    }
}
