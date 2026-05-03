package com.credentialvault.domain.model;

import com.credentialvault.application.dto.credential.ResponseCredential;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Credential(String site, String login, String encryptedPassword) {
        this.site = site;
        this.login = login;
        this.encryptedPassword = encryptedPassword;
    }
}
