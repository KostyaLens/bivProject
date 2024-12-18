package org.example.validatros;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.example.entity.Bank;
import org.example.entity.User;
import org.example.security.AuthenticationFacade;
import org.example.services.AccountService;
import org.example.services.BankService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class NotBankClientValidator implements ConstraintValidator<NotBankClient, String> {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @Override
    public void initialize(NotBankClient constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @SneakyThrows
    @Override
    public boolean isValid(String nameBank, ConstraintValidatorContext constraintValidatorContext) {
        String username = authenticationFacade.getCurrentUserName();
        User user = userService.getByUsername(username);
        Bank bank = bankService.getBankByName(nameBank);
        return accountService.getAccountByUserAndBank(user, bank).isEmpty();
    }
}
