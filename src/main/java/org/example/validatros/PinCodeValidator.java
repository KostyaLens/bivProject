package org.example.validatros;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PinCodeValidator implements ConstraintValidator<PinCode, String> {
    private static final String PINCODE ="^\\d{4}$";

    @Override
    public void initialize(PinCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches(PINCODE);
    }
}
