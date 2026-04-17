package com.credentialvault.jwt;

import com.credentialvault.domain.UserAccount;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private UserAccount userAccount;

    public JwtUserDetails(UserAccount userAccount) {
        super(userAccount.getEmail(), userAccount.getPassword(), AuthorityUtils.createAuthorityList(userAccount.getRole().name()));
        this.userAccount = userAccount;
    }

    public Long getId() {
        return this.userAccount.getId();
    }

    public String getRole() {
        return this.userAccount.getRole().name();
    }
}
