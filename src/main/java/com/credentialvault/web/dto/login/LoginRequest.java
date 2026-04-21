package com.credentialvault.web.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    @Email(message = "Email format invalid.")
    private String email;

    @NotBlank
    @Size(min = 5, max = 12, message = "Password must be in 5 and 12 characters.")
    private String password;
}
