package com.credentialvault.repository;

import com.credentialvault.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select u.role from UserAccount u where u.email like :email")
    UserAccount.Role findRoleByEmail(String email);
}
