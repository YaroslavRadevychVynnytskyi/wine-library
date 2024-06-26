package com.application.winelibrary.validation.validator;

import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.validation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchValidator implements
        ConstraintValidator<PasswordMatch, UserRegistrationRequestDto> {
    @Override
    public boolean isValid(
            UserRegistrationRequestDto requestDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return Objects.equals(requestDto.getPassword(), requestDto.getRepeatedPassword());
    }
}
