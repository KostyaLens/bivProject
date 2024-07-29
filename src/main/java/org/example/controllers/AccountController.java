package org.example.controllers;


import org.example.dto.*;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.exception.NotFoundUserException;
import org.example.exception.WrongPinCodeException;
import org.example.mappers.AccountMapper;
import org.example.security.AuthenticationFacade;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController{

    private final AccountService accountService;

    private final UserService userService;

    private final AccountMapper accountMapper;

    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/created")
    public AccountDto createAccount(@Valid @RequestBody CreateAccountDto createAccountDto) throws NotFoundUserException {
        User user = userService.getByUsername(authenticationFacade.getCurrentUserName());
        Account account = accountMapper.toEntity(createAccountDto);
        account.setActive(true);
        account.setUser(user);
        accountService.createAccount(account);
        return accountMapper.toDto(account);
    }

    @GetMapping("/info")
    public AccountDto infoAccount(@RequestBody @Valid RequestInfoAccountDto accountDto)
            throws WrongPinCodeException, NotFoundUserException {
        Account account = getAccount(authenticationFacade.getCurrentUserName());
        accountService.checkPinCode(account, accountDto.getPinCode());
        return accountMapper.toDto(account);
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody @Valid RequestAccountDto requestAccountDto) throws WrongPinCodeException, NotFoundUserException {
        Account account = getAccount(authenticationFacade.getCurrentUserName());
        accountService.deposit(account, requestAccountDto.getPinCode(), requestAccountDto.getAmount());
        return ResponseEntity.ok("Счёт пополнен");
    }

    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody @Valid RequestAccountDto requestAccountDto)
            throws NotEnoughFundsException, WrongPinCodeException, NotFoundUserException {
        Account account = getAccount(authenticationFacade.getCurrentUserName());
        accountService.withdraw(account, requestAccountDto.getPinCode(), requestAccountDto.getAmount());
        return ResponseEntity.ok("Деньги с счёта списаны успешно");
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferDto transferDto)
            throws NotEnoughFundsException, WrongPinCodeException, NotFoundUserException {
        Account sender = getAccount(authenticationFacade.getCurrentUserName());
        Account recipient = getAccount(transferDto.getRecipientUsername());
        accountService.transfer(sender, recipient, transferDto.getPinCode(), transferDto.getAmount());
        return ResponseEntity.ok("Перевод выполнен");
    }

    @DeleteMapping("/block-account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> blockAccount(@RequestBody @Valid BlockAccountDto blockAccountDto) throws NotFoundUserException {
        Account account = getAccount(blockAccountDto.getUsername());
        accountService.blockAccount(account.getId());
        return ResponseEntity.ok("Аккаунт пользователя заблокирован");
    }


    private Account getAccount(String username) throws NotFoundUserException {
        User user = userService.getByUsername(username);
        return accountService.getAccountByUser(user);
    }
}
