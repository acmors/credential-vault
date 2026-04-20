package com.credentialvault.repository;

import com.credentialvault.domain.Credential;
import com.credentialvault.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    List<Credential> findAllByUser(UserAccount user);
}
