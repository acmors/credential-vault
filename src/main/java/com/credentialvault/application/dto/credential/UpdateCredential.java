package com.credentialvault.application.dto.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCredential {

    private String site;
    private String login;
    private String encryptedPassword;
}

