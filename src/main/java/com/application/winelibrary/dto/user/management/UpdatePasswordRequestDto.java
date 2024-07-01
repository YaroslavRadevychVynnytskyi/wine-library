package com.application.winelibrary.dto.user.management;

import com.application.winelibrary.validation.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@PasswordMatch
@Data
public class UpdatePasswordRequestDto {
    @NotBlank
    @Length(min = 8, max = 35)
    private String currentPassword;

    @NotBlank
    @Length(min = 8, max = 35)
    private String password;

    @NotBlank
    @Length(min = 8, max = 35)
    private String repeatedPassword;
}
