package com.credentialvault.common.mapper;

import com.credentialvault.domain.model.Credential;
import com.credentialvault.application.service.CryptoService;
import com.credentialvault.application.dto.credential.ResponseCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperCredential {

    private final CryptoService cryptoService;

    public ResponseCredential toDto(Credential credential){
        return new ResponseCredential(
                credential.getSite(),
                credential.getLogin(),
                cryptoService.decryptPassword(credential.getEncryptedPassword())
        );
    }
}
