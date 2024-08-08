package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Пользователь с таким username уже зарегестрирован";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}