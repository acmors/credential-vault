package com.credentialvault.application.service;
import com.credentialvault.domain.model.Credential;
import com.credentialvault.domain.model.UserAccount;
import com.credentialvault.common.exceptions.business.ResourceNotFoundException;
import com.credentialvault.application.service.validation.CredentialValidations;
import com.credentialvault.infra.repository.CredentialRepository;
import com.credentialvault.application.dto.credential.CreateCredential;
import com.credentialvault.application.dto.credential.ResponseCredential;
import com.credentialvault.application.dto.credential.UpdateCredential;
import com.credentialvault.common.mapper.MapperCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final CredentialRepository repository;
    private final UserAccountService userAccountService;
    private final CryptoService cryptoService;
    private final MapperCredential mapperCredential;
    private final CredentialValidations credentialValidation;

    @Transactional
    public ResponseCredential createCredential(CreateCredential dto, String email){
        UserAccount user = userAccountService.findByEmailEntity(email);
        Credential credential = new Credential();

        credential.setSite(dto.getSite());
        credential.setLogin(dto.getLogin());
        credential.setEncryptedPassword(cryptoService.encryptPassword(dto.getEncryptedPassword()));
        credential.setCreatedAt(LocalDateTime.now());
        credential.setUser(user);

        var created = repository.save(credential);
        return mapperCredential.toDto(created);
    }

    @Transactional
    public ResponseCredential updateCredential(Long id, UpdateCredential update, String email){
        Credential credential = findById(id);
        credentialValidation.validateOwner(credential, email);

        credential.setSite(update.getSite());
        credential.setLogin(update.getLogin());
        credential.setEncryptedPassword(cryptoService.encryptPassword(update.getEncryptedPassword()));
        credential.setUpdatedAt(LocalDateTime.now());

        var created = repository.save(credential);
        return mapperCredential.toDto(created);
    }

    @Transactional(readOnly = true)
    public Credential findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    @Transactional(readOnly = true)
    public Page<ResponseCredential> findAllCredentialsByUser(String email, Pageable pageable){
        UserAccount user = userAccountService.findByEmailEntity(email);
        return repository
                .findAllByUser(user, pageable)
                .map(mapperCredential::toDto);
    }

    @Transactional
    public void deleteCredential(Long id, String email){
        Credential credential = findById(id);
        credentialValidation.validateOwner(credential, email);
        repository.delete(credential);
    }

}

