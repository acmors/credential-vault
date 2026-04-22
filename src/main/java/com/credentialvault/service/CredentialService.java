package com.credentialvault.service;
import com.credentialvault.domain.Credential;
import com.credentialvault.domain.UserAccount;
import com.credentialvault.exceptions.AccessDeniedException;
import com.credentialvault.exceptions.ResourceNotFoundException;
import com.credentialvault.repository.CredentialRepository;
import com.credentialvault.web.dto.credential.CreateCredential;
import com.credentialvault.web.dto.credential.ResponseCredential;
import com.credentialvault.web.dto.credential.UpdateCredential;
import com.credentialvault.web.mapper.MapperCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        Credential credential =findById(id);
        validateOwner(credential, email);

        credential.setSite(update.getSite());
        credential.setLogin(update.getLogin());
        credential.setEncryptedPassword(update.getEncryptedPassword());
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
    public List<ResponseCredential> findAllCredentialsByUser(String email){
        UserAccount user = userAccountService.findByEmailEntity(email);
        return repository.findAllByUser(user)
                .stream()
                .map(mapperCredential::toDto)
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
            throw new AccessDeniedException("Access denied.");
        }
    }
}

