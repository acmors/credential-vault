package com.credentialvault.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.credentialvault.domain.model.Credential;
import com.credentialvault.domain.model.UserAccount;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CredentialRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CredentialRepository repository;

    @Test
    @DisplayName("Should return credentials with pagination")
    void shouldReturnCredentialsWithPagination() {

        UserAccount user = new UserAccount();
        user.setEmail("test@gmail.com");

        entityManager.persist(user);

        Credential credential = new Credential();
        credential.setUser(user);
        credential.setSite("github");
        credential.setLogin("userGitHub@gmail.com");
        credential.setEncryptedPassword("123456789");

        entityManager.persist(credential);

        Pageable pageable = PageRequest.of(0,10);
        var result = repository.findAllByUser(user, pageable);

        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getSite()).isEqualTo("github");
    }

    @Test
    @DisplayName("Should not return credentials with pagination")
    void shouldReturnEmptyPaginationWhenUserHasNoCredentials() {

        UserAccount user = new UserAccount();
        user.setEmail("test@gmail.com");
        entityManager.persist(user);

        Pageable pageable = PageRequest.of(0,10);
        var result = repository.findAllByUser(user, pageable);

        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Should not return credentials from another user")
    void shouldNoReturnCredentialsFromAnotherUser(){
        UserAccount userA = new UserAccount();
        userA.setEmail("a@gmail.com");
        entityManager.persist(userA);

        UserAccount userB = new UserAccount();
        userB.setEmail("b@gmail.com");
        entityManager.persist(userB);

        Credential credential = new Credential();
        credential.setUser(userB);
        credential.setSite("GitHub");
        credential.setLogin("userB@gmail.com");
        credential.setEncryptedPassword("123456789");
        entityManager.persist(credential);

        Pageable page = PageRequest.of(0,10);
        var result = repository.findAllByUser(userA, page);

        assertThat(result.getContent()).isEmpty();
    }

}