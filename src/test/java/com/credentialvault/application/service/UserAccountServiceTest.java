package com.credentialvault.application.service;

import com.credentialvault.application.dto.user.CreateUserAccount;
import com.credentialvault.application.dto.user.ResponseUserAccount;
import com.credentialvault.application.dto.user.UpdateUserEmailAndUsername;
import com.credentialvault.application.dto.user.UpdateUserPassword;
import com.credentialvault.application.service.validation.UserValidation;
import com.credentialvault.common.exceptions.business.BadRequestException;
import com.credentialvault.common.exceptions.business.EmailAlreadyExistsException;
import com.credentialvault.common.exceptions.business.ResourceNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Should update a username and email successfully")
    void shouldUpdateUsernameAndEmailSuccessfully(){

        String email = "userTest@gmail.com";

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(repository.save(user))
                .thenReturn(user);

        ResponseUserAccount result = service.updateUserEmailAndUsername(updateEmailAndUsername, email);

        assertNotNull(result);
        assertEquals("User Test", user.getUsername());
        assertEquals("userTestado@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("Shouldnt update when user dont exists")
    void shouldntUpdateUsernameAndEmailWhenUserDontExists(){

        String email = "Test@gmail.com";

        when(repository.findByEmail(email))
                .thenThrow(new ResourceNotFoundException("User not found. May do not registered"));

        assertThrows(ResourceNotFoundException.class, ()->
            service.updateUserEmailAndUsername(updateEmailAndUsername, email)
        );
    }

    @Test
    @DisplayName("Should update password successfully")
    void shouldUpdatePasswordSuccessfully(){

        String email = "userTest@gmail.com";

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(encoder.matches(updatePassword.getCurrentPassword(), user.getPassword()))
                .thenReturn(true);

        when(encoder.encode(updatePassword.getNewPassword()))
                .thenReturn("newEncodedPassword");

        when(repository.save(user))
                .thenReturn(user);

        ResponseUserAccount result = service.updateUserPassword(updatePassword, email);

        assertNotNull(result);
        assertEquals("newEncodedPassword", user.getPassword());

    }

    @Test
    @DisplayName("Shouldn update password when current ir wrong")
    void shouldntUpdatePasswordWhenCurrentPasswordIsWrong(){

        String email = "userTest@gmail.com";
        updatePassword.setCurrentPassword("234567891");

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(encoder.matches(updatePassword.getCurrentPassword(), user.getPassword()))
                .thenReturn(false);

        assertThrows(BadRequestException.class, ()->
            service.updateUserPassword(updatePassword, email)
        );

    }

}