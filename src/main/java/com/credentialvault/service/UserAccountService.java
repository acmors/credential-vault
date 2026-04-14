package com.credentialvault.service;

import com.credentialvault.domain.UserAccount;
import com.credentialvault.repository.UserAccountRepository;
import com.credentialvault.web.dto.CreateUserAccount;
import com.credentialvault.web.dto.ResponseUserAccount;
import com.credentialvault.web.mapper.MapperUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository repository;

    @Transactional
    public ResponseUserAccount createUserAccount(CreateUserAccount createUser){
        UserAccount user = new UserAccount();
        if (existsByEmail(createUser.getEmail())){
            throw new RuntimeException("Email already exists!");
        }

        user.setUsername(createUser.getUsername());
        user.setEmail(createUser.getEmail());
        user.setPassword(createUser.getPassword());

        var saved = repository.save(user);
        return MapperUserAccount.toDTO(saved);

    }

    //para consultas internas sem necessidade de DTO
    @Transactional(readOnly = true)
    public UserAccount findByEmailEntity(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));
    }

    //para consultas externas com necessidade de DTO
    @Transactional(readOnly = true)
    public ResponseUserAccount findByEmail(String email){
        UserAccount user = findByEmailEntity(email);
        return MapperUserAccount.toDTO(user);
    }

    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }
}
