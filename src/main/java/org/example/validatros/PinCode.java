package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = PinCodeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PinCode {
    String message() default "Пин-код состоит из 4-х цифр";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}