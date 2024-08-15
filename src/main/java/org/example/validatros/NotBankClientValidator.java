package org.example.validatros;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.repository.UserRepository;
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


    @Override
    public boolean isValid(String nameBank, ConstraintValidatorContext constraintValidatorContext) {
//        Account account = null;
//        String userName = authenticationFacade.getCurrentUserName();
//        User user = null;
//        try {
//            user = userService.getByUsername(userName);
//        } catch (NotFoundUserOrAccountException e) {
//            throw new RuntimeException(e);
//        }
//        if (accountService.getAccountByUser(user).isPresent()) {
//            try {
//                account = accountService.getAccountByUser(user).orElseThrow(() -> new NotFoundUserOrAccountException("Не найденно аккаунта"));
//            } catch (NotFoundUserOrAccountException e) {
//                throw new RuntimeException(e);
//            }
//        }else {
//            return true;
//        }
//        if(bankService.getBankByNameAndByAccount(nameBank, account).isEmpty()){
//            System.out.println("red");
//        }
////        System.out.println(bankService.getBankByNameAndByAccount(nameBank, account).orElseThrow());
//        return bankService.getBankByNameAndByAccount(nameBank, account).isEmpty();
    return false;
    }
}
