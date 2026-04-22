package com.credentialvault.web.mapper;

import com.credentialvault.domain.Credential;
import com.credentialvault.service.CryptoService;
import com.credentialvault.web.dto.credential.ResponseCredential;
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
