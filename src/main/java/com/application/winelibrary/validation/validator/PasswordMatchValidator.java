package com.application.winelibrary.validation.validator;

import com.application.winelibrary.dto.user.management.UpdatePasswordRequestDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.validation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object instanceof UserRegistrationRequestDto requestDto) {
            return Objects.equals(requestDto.getPassword(), requestDto.getRepeatedPassword());
        }
        if (object instanceof UpdatePasswordRequestDto requestDto) {
            return Objects.equals(requestDto.getPassword(), requestDto.getRepeatedPassword());
        }
        return false;
    }
}
