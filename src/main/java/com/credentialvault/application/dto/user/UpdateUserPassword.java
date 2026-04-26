package com.credentialvault.application.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPassword {

    @NotBlank
    @Size(min = 5, max = 12, message = "Password must be in 5 and 12 characters.")
    private String currentPassword;

    @NotBlank
    @Size(min = 5, max = 12, message = "Password must be in 5 and 12 characters.")
    private String newPassword;

    @NotBlank
    @Size(min = 5, max = 12, message = "Password must be in 5 and 12 characters.")
    private String confirmPassword;

}
