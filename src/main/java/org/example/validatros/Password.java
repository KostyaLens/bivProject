package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Пароль должен содержать символы верхнего и нижнего регистра, цифры, a также иметь длину не мение 8 символов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
