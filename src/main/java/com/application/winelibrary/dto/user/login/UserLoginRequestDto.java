package com.application.winelibrary.dto.user.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotBlank
        @Email
        String email,
        @Length(min = 8, max = 35)
        String password
) {
}
