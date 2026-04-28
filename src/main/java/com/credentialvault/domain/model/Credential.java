package com.credentialvault.domain.model;

import com.credentialvault.application.dto.credential.ResponseCredential;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String site;

    private String login;

    private String encryptedPassword;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Credential() {
    }

    public Credential(Long id, String site, String login, String encryptedPassword, UserAccount user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.site = site;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
