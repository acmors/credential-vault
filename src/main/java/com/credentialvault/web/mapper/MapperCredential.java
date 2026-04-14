package com.credentialvault.web.mapper;

import com.credentialvault.domain.Credential;
import com.credentialvault.web.dto.credential.ResponseCredential;

public class MapperCredential {
    public static ResponseCredential toDto(Credential credential){
        return new ResponseCredential(
                credential.getSite(),
                credential.getLogin(),
                credential.getEncryptedPassword()
        );
    }
}
