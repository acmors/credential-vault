package com.credentialvault.application.dto.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCredential {

    private String site;
    private String login;
    private String password;

    public ResponseCredential(String github, String mail) {
    }
}
