package com.credentialvault.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserEmailAndUsername {


    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "Email format invalid.")
    private String email;

}
