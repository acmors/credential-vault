package com.credentialvault.application.service;

import com.credentialvault.application.dto.user.CreateUserAccount;
import com.credentialvault.application.dto.user.ResponseUserAccount;
import com.credentialvault.application.dto.user.UpdateUserEmailAndUsername;
import com.credentialvault.application.dto.user.UpdateUserPassword;
import com.credentialvault.application.service.validation.UserValidation;
import com.credentialvault.common.exceptions.business.EmailAlreadyExistsException;
import com.credentialvault.domain.model.UserAccount;
import com.credentialvault.infra.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private UserAccountRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserValidation validation;

    @InjectMocks
    private UserAccountService service;

    private UserAccount user;
    private CreateUserAccount request;
    private ResponseUserAccount response;
    private UpdateUserEmailAndUsername updateEmailAndUsername;
    private UpdateUserPassword updatePassword;

    @BeforeEach
    public void setup(){
        user = new UserAccount(
                1L,
                "UserTest",
                "userTest@gmail.com",
                "encodedPassword",
                UserAccount.Role.CLIENTE
        );

        request = new CreateUserAccount(
                "UserTest",
                "userTest@gmail.com",
                "123456789"
        );

        response = new ResponseUserAccount(
                "UserTest",
                "userTest@gmail.com"
        );

        updateEmailAndUsername = new UpdateUserEmailAndUsername(
                "User Test",
                "userTestado@gmail.com"
        );

        updatePassword = new UpdateUserPassword(
                "123456789",
                "1234567890",
                "1234567890"
        );
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully(){

        when(encoder.encode(request.getPassword()))
                .thenReturn("encodedPassword");

        when(repository.save(any(UserAccount.class)))
                .thenReturn(user);

        var response = service.createUserAccount(request);

        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(request.getEmail(), response.getEmail());
    }

    @Test
    @DisplayName("Shouldn create a user that email already exists")
    void shouldntCreateUserEmailAlreadyExists(){

        doThrow(new EmailAlreadyExistsException("Email already registered"))
                .when(validation)
                .existsByEmail(request.getEmail());

        assertThrows(EmailAlreadyExistsException.class, ()->
            service.createUserAccount(request)
        );
    }

}