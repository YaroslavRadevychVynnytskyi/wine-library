package com.application.winelibrary.dto.user.management;

import com.application.winelibrary.validation.ValidName;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateUserLastNameRequestDto(
        @NotBlank
        @ValidName
        @Length(min = 2, max = 70)
        String lastName
) {
}
