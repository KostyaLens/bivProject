package org.example.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.example.dto.AccountDto;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.exception.WrongPinCodeException;
import org.example.mappers.AccountMapper;
import org.example.mappers.UserMapper;
import org.example.security.IAuthenticationFacade;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.example.validatros.PinCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController{

    private final AccountService accountService;

    private final UserService userService;

    private final AccountMapper accountMapper;

    private final IAuthenticationFacade authenticationFacade;

    @PostMapping("/createdAccount")
    public AccountDto createAccount(@Valid @RequestBody AccountDto accountDto){
        accountDto.setActive(true);
        User user = userService.getByUsername(authenticationFacade.getAuthentication().getName());
        Account account = accountMapper.toEntity(accountDto);
        account.setUser(user);
        accountService.createAccount(account);
        return accountDto;
    }

    @GetMapping("/getBalance")
    public long getBalance(@RequestParam @PinCode @NotEmpty(message = "Не введён пин-код") String pinCode) throws WrongPinCodeException {
        Account account = getAccount(authenticationFacade.getAuthentication().getName());
        if (!pinCode.equals(account.getPinCode())){
            throw new WrongPinCodeException("Неверный пин-код");
        }
        AccountDto accountDto = accountMapper.toDto(account);
        return accountDto.getBalance();
    }

    @PutMapping("/upBalance")
    public void upBalance(@RequestParam @PinCode @NotEmpty(message = "Не введён пин-код") String pinCode, @RequestParam @Min(value = 0, message = "Не возможно пополнить баланс отрицаьтельной суммой") long amount) throws WrongPinCodeException {
        Account account = getAccount(authenticationFacade.getAuthentication().getName());
        if (!pinCode.equals(account.getPinCode())){
            throw new WrongPinCodeException("Неверный пин-код");
        }
        accountService.upBalance(account, pinCode, amount);
    }

    @PutMapping("/downBalance")
    public void downBalance(@RequestParam @PinCode @NotEmpty(message = "Не введён пин-код") String pinCode, @RequestParam @Min(value = 0, message = "Не возможно снять с баланса отрицательную сумму") long amount) throws NotEnoughFundsException, WrongPinCodeException {
        Account account = getAccount(authenticationFacade.getAuthentication().getName());
        if (!pinCode.equals(account.getPinCode())){
            throw new WrongPinCodeException("Неверный пин-код");
        }
        accountService.downBalance(account, pinCode, amount);
    }

    @PutMapping("/transfer")
    public void transfer(@RequestParam String recipientUsername, @RequestParam @Min(value = 0, message = "Не возможно перевемсти на другой счёт отрицательную сумму")  long amount, @PinCode @NotEmpty(message = "Не введён пин-код") String pinCode) throws NotEnoughFundsException, WrongPinCodeException {
        Account sender = getAccount(authenticationFacade.getAuthentication().getName());
        if (!pinCode.equals(sender.getPinCode())){
            throw new WrongPinCodeException("Неверный пин-код");
        }
        accountService.downBalance(sender, pinCode, amount);
        Account recipient = getAccount(recipientUsername);
        accountService.upBalance(recipient, amount);
    }

    private Account getAccount(String username){
        User user = userService.getByUsername(username);
        return accountService.getAccountByUser(user);
    }
}
