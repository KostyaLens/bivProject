package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Пользователь с таким email уже зарегестрирован";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}