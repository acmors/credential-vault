package com.credentialvault.application.dto.credential;

import jakarta.validation.constraints.Email;
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
    @Email(message = "Email format invalid.")
    private String login;
    private String encryptedPassword;
}

