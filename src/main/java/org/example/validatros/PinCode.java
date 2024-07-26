package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = PinCodeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PinCode {
    String message() default "Пин-код состоит из 4-х цифр";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}