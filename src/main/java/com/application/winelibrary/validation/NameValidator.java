package com.application.winelibrary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return false;
        }
        return name.matches("^[a-zA-Z][a-zA-Z0-9-]{2,39}$");
    }
}
