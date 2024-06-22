package com.application.winelibrary.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "must start with a letter, have no "
            + "spaces, and be 3-40 characters long";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
