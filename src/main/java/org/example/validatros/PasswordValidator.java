package org.example.validatros;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean low = false;
        boolean high = false;
        boolean digit = false;
        for (char c: s.toCharArray()){
            if((c >= 'a') && (c <= 'z')){
                low = true;
            }else if (c >= 'A' && c <= 'Z'){
                high = true;
            }else if (c >= '0' && c <= '9'){
                digit = true;
            }
        }
        return (low && digit && high);
    }
}
