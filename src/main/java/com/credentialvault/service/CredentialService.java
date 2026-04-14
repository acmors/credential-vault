package com.credentialvault.service;

import com.credentialvault.domain.Credential;
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

    @Transactional
    public ResponseCredential createCredential(CreateCredential dto){
        Credential credential = new Credential();

        credential.setSite(dto.getSite());
        credential.setLogin(dto.getLogin());
        credential.setEncryptedPassword(dto.getEncryptedPassword());
        credential.setCreatedAt(LocalDateTime.now());

        var created = repository.save(credential);
        return MapperCredential.toDto(created);
    }

    @Transactional(readOnly = true)
    public List<ResponseCredential> findAllCredentials(){
        return repository.findAll()
                .stream()
                .map(MapperCredential::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseCredential updateCredentials(UpdateCredential update){
        Credential credential = new Credential();

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
}

