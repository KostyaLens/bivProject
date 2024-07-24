package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidotor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Пароль должен содержать символы верхнего и нижнего регистра, а также цифры";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
