package com.credentialvault.application.service;

import com.credentialvault.domain.model.UserAccount;
import com.credentialvault.common.exceptions.business.BadRequestException;
import com.credentialvault.common.exceptions.business.ResourceNotFoundException;
import com.credentialvault.application.service.validation.UserValidation;
import com.credentialvault.infra.repository.UserAccountRepository;
import com.credentialvault.application.dto.user.CreateUserAccount;
import com.credentialvault.application.dto.user.ResponseUserAccount;
import com.credentialvault.application.dto.user.UpdateUserEmailAndUsername;
import com.credentialvault.application.dto.user.UpdateUserPassword;
import com.credentialvault.common.mapper.MapperUserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder encoder;
    private final UserValidation userValidation;

    @Transactional
    public ResponseUserAccount createUserAccount(CreateUserAccount createUser){
        UserAccount user = new UserAccount();
        userValidation.existsByEmail(createUser.getEmail());

        user.setUsername(createUser.getUsername());
        user.setEmail(createUser.getEmail());
        user.setRole(UserAccount.Role.CLIENTE);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encoder.encode(createUser.getPassword()));

        var saved = repository.save(user);
        return MapperUserAccount.toDTO(saved);

    }

    @Transactional
    public ResponseUserAccount updateUserEmailAndUsername(UpdateUserEmailAndUsername update, String email){
        UserAccount user = findByEmailEntity(email);
        userValidation.existsByEmail(update.getEmail());

        user.setUsername(update.getUsername());
        user.setEmail(update.getEmail());

        var saved = repository.save(user);
        return MapperUserAccount.toDTO(saved);
    }

    @Transactional
    public ResponseUserAccount updateUserPassword(UpdateUserPassword password, String email){
        UserAccount user = findByEmailEntity(email);

        if (!encoder.matches(password.getCurrentPassword(), user.getPassword())) throw new BadRequestException("Password is wrong.");
        if(!password.getNewPassword().equals(password.getConfirmPassword())) throw new BadRequestException("Password dont match.");

        user.setPassword(encoder.encode(password.getNewPassword()));
        var saved = repository.save(user);

        return MapperUserAccount.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public UserAccount findByEmailEntity(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));
    }

    @Transactional(readOnly = true)
    public ResponseUserAccount findByEmail(String email){
        UserAccount user = findByEmailEntity(email);
        return MapperUserAccount.toDTO(user);
    }

    @Transactional
    public UserAccount.Role findRoleByUsername(String email){
        return repository.findRoleByEmail(email);
    }

}
