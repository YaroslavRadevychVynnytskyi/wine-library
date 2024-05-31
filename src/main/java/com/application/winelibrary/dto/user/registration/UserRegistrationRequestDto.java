package com.application.winelibrary.dto.user.registration;

import com.application.winelibrary.validation.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@PasswordMatch
public class UserRegistrationRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 35)
    private String password;
    @NotBlank
    @Length(min = 8, max = 35)
    private String repeatedPassword;
}
