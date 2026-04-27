package com.credentialvault.infra.repository;

import com.credentialvault.domain.model.Credential;
import com.credentialvault.domain.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Page<Credential> findAllByUser(UserAccount user, Pageable pageable);
}
