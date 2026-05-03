package com.credentialvault.application.service;
import com.credentialvault.application.dto.credential.CreateCredential;
import com.credentialvault.application.dto.credential.ResponseCredential;
import com.credentialvault.application.dto.credential.UpdateCredential;
import com.credentialvault.application.service.validation.CredentialValidations;
import com.credentialvault.common.exceptions.business.ResourceNotFoundException;
import com.credentialvault.common.mapper.MapperCredential;
import com.credentialvault.domain.model.Credential;
import com.credentialvault.domain.model.UserAccount;
import com.credentialvault.infra.repository.CredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CredentialServiceTest {

    @Mock
    private CredentialRepository repository;

    @Mock
    private UserAccountService userService;

    @Mock
    private CryptoService cryptoService;

    @Mock
    private MapperCredential mapper;

    @Mock
    private CredentialValidations validations;

    @InjectMocks
    private CredentialService service;

    private UserAccount user;
    private CreateCredential request;
    private ResponseCredential response;
    private Credential credentialEntity;
    private UpdateCredential update;

    @BeforeEach
    public void setup(){
        user = new UserAccount(
                1L,
                "UserTest",
                "userTest@gmail.com",
                "123456789",
                UserAccount.Role.CLIENTE
        );
        request = new CreateCredential(
                "Github",
                "user@gmail.com",
                "123456");

        response = new ResponseCredential(
                "Github",
                "user@gmail.com");

        credentialEntity = new Credential();

        update = new UpdateCredential(
                "Youtube",
                "youtubeLogin@gmail.com",
                "123456789"
        );

    }

    @Test
    @DisplayName("Should create credential successfully")
    void shouldCreateCredentialSucessfully() {

        //arrange
        when(userService.findByEmailEntity(user.getEmail())).thenReturn(user);
        when(cryptoService.encryptPassword("123456")).thenReturn("encryptedPassword");
        when(repository.save(any(Credential.class))).thenReturn(credentialEntity);
        when(mapper.toDto(credentialEntity)).thenReturn(response);

        //act
        var response = service.createCredential(request, user.getEmail());

        //assert
        assertNotNull(response);
        assertEquals("user@gmail.com", request.getLogin());
    }

    @Test
    @DisplayName("Should return Resource not found when user don't exists")
    void shouldntCreateCredentialWhenUserDontExists() {

        String email = "user@gmail.com";

        when(userService.findByEmailEntity(email))
                .thenThrow(new ResourceNotFoundException("User not found. May do not registered"));

        assertThrows(ResourceNotFoundException.class, () ->
                service.createCredential(request, "user@gmail.com"));

    }

    @Test
    @DisplayName("Should update credential successfully")
    void shouldUpdateCredentialSuccessfully() {

        Long credentialId = 1L;
        String email = "user@gmail.com";

        //create credential
        when(repository.findById(credentialId))
                .thenReturn(Optional.of(credentialEntity));

        doNothing()
                .when(validations)
                .validateOwner(credentialEntity, email);

        when(cryptoService.encryptPassword("123456789"))
                .thenReturn("encryptedPassword");

        when(repository.save(credentialEntity))
                .thenReturn(credentialEntity);

        when(mapper.toDto(credentialEntity))
                .thenReturn(response);

        var response = service.updateCredential(credentialId, update, email);

        assertNotNull(response);

        verify(repository).findById(credentialId);
        verify(validations).validateOwner(credentialEntity, email);
        verify(cryptoService).encryptPassword("123456789");
        verify(repository).save(credentialEntity);
        verify(mapper).toDto(credentialEntity);
    }

    @Test
    @DisplayName("Should return Resource not Found when credential dont exists")
    void shouldntUpdateWhenCredentialDontExist(){
        String email = "user@gmail.com";
        Long credentialId = 2L;

        when(repository.findById(credentialId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.updateCredential(credentialId, update, email));
    }

    @Test
    @DisplayName("Should return credentials by user")
    void shouldReturnCredentialsByUser(){
        String email = "user@gmail.com";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Credential> credentialPage = new PageImpl<>(List.of(credentialEntity));

        when(userService.findByEmailEntity(email))
                .thenReturn(user);

        when(repository.findAllByUser(user, pageable))
                .thenReturn(credentialPage);

        when(mapper.toDto(credentialEntity))
                .thenReturn(response);

        Page<ResponseCredential> result = service.findAllCredentialsByUser(email, pageable);

       assertNotNull(result);
       assertEquals(1, result.getTotalElements());
       assertEquals(response, result.getContent().get(0));
    }

    @Test
    @DisplayName("Should delete a credential successfully.")
    void shouldDeleteCredentialSuccessfully(){
        Long credentialId = 1L;
        String email = "userTest@email.com";

        when(repository.findById(credentialId))
                .thenReturn(Optional.of(credentialEntity));
        service.deleteCredential(credentialId, email);

        verify(repository).delete(credentialEntity);

    }

    @Test
    @DisplayName("Shouldnt delete a credential successfully.")
    void shouldntDeleteWhenCredentialDontExists(){
        Long credentialId = 2L;
        String email = "userTest@email.com";

        when(repository.findById(credentialId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.deleteCredential(credentialId, email));
    }

}