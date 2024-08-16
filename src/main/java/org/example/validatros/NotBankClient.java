package org.example.validatros;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotBankClientValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBankClient {
    String message() default "Данный аккаунт уже является пользователём данного банка";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
