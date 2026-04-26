package com.credentialvault.infra.repository;

import com.credentialvault.domain.model.Credential;
import com.credentialvault.domain.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    List<Credential> findAllByUser(UserAccount user);
}
