package com.application.winelibrary.validation.validator;

import com.application.winelibrary.validation.ValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return false;
        }
        return name.matches("^[a-zA-Zа-яА-ЯїЇіІєЄґҐ][a-zA-Zа-яА-ЯїЇіІєЄґҐ0-9-]{2,39}$");
    }
}
