package com.credentialvault.web.dto.credential;

import jakarta.validation.constraints.NotBlank;

public class CreateCredential {

    @NotBlank
    private String site;

    @NotBlank
    private String login;

    @NotBlank
    private String encryptedPassword;

    public CreateCredential(String site, String login, String encryptedPassword) {
        this.site = site;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
