package com.credentialvault.service;

import com.credentialvault.domain.Credential;
import com.credentialvault.domain.UserAccount;
import com.credentialvault.repository.CredentialRepository;
import com.credentialvault.web.dto.credential.CreateCredential;
import com.credentialvault.web.dto.credential.ResponseCredential;
import com.credentialvault.web.dto.credential.UpdateCredential;
import com.credentialvault.web.mapper.MapperCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository repository;

    @Autowired
    private UserAccountService userAccountService;

    @Transactional
    public ResponseCredential createCredential(CreateCredential dto, String email){
        UserAccount user = userAccountService.findByEmailEntity(email);
        Credential credential = new Credential();

        credential.setSite(dto.getSite());
        credential.setLogin(dto.getLogin());
        credential.setEncryptedPassword(dto.getEncryptedPassword());
        credential.setCreatedAt(LocalDateTime.now());
        credential.setUser(user);

        var created = repository.save(credential);
        return MapperCredential.toDto(created);
    }

    @Transactional
    public ResponseCredential updateCredential(Long id, UpdateCredential update, String email){
        Credential credential =findById(id);
        validateOwner(credential, email);

        credential.setSite(update.getSite());
        credential.setLogin(update.getLogin());
        credential.setEncryptedPassword(update.getEncryptedPassword());
        credential.setUpdatedAt(LocalDateTime.now());

        var created = repository.save(credential);
        return MapperCredential.toDto(created);
    }

    @Transactional(readOnly = true)
    public Credential findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Transactional(readOnly = true)
    public List<ResponseCredential> findAllCredentialsByUser(String email){
        UserAccount user = userAccountService.findByEmailEntity(email);
        return repository.findAllByUser(user)
                .stream()
                .map(MapperCredential::toDto)
                .toList();
    }

    @Transactional
    public void deleteCredential(Long id, String email){
        Credential credential = findById(id);
        validateOwner(credential, email);

        repository.delete(credential);
    }

    private void validateOwner(Credential credential, String email){
        if (credential.getUser() == null ||!credential.getUser().getEmail().equals(email)){
            throw new RuntimeException("Access denied.");
        }
    }
}

