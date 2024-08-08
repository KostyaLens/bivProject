package org.example.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.NonUniqueResultException;
import jakarta.validation.constraints.NotEmpty;
import org.example.dto.*;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.exception.NotFoundBankException;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.exception.WrongPinCodeException;
import org.example.mappers.AccountMapper;
import org.example.security.AuthenticationFacade;
import org.example.services.AccountService;
import org.example.services.BankService;
import org.example.services.UserService;
import org.example.validatros.PinCode;
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
@Tag(name = "Account Controller", description = "Account API")
public class AccountController{

    private final AccountService accountService;

    private final UserService userService;

    private final BankService bankService;

    private final AccountMapper accountMapper;

    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/created")
    @Operation(summary = "Creating an account")
    public AccountDto createAccount(@Valid @RequestBody CreateAccountDto createAccountDto)
            throws NotFoundUserOrAccountException, NotFoundBankException {
        User user = userService.getByUsername(authenticationFacade.getCurrentUserName());
        if (accountService.getAccountByUser(user) != null) {
            throw new NonUniqueResultException("У данного пользователя уже сущевствует аккаунт");
        }
        Account account = accountMapper.toEntity(createAccountDto);
        account.setActive(true);
        account.setBank(bankService.getBankByName(createAccountDto.getBank()));
        account.setUser(user);
        accountService.createAccount(account);
        return accountMapper.toDto(account);
    }

    @GetMapping("/info")
    @Operation(summary = "Account Information")
    public AccountDto infoAccount(@RequestParam @PinCode @NotEmpty(message = "Не введён пин-код") String pinCode)
            throws WrongPinCodeException, NotFoundUserOrAccountException {
        Account account = getAccount(authenticationFacade.getCurrentUserName());
        accountService.checkPinCode(account, pinCode);
        return accountMapper.toDto(account);
    }

    @PutMapping("/deposit")
    @Operation(summary = "The method of replenishment of the account")
     public ResponseEntity<String> deposit(@RequestBody @Valid RequestAccountDto requestAccountDto) throws WrongPinCodeException, NotFoundUserOrAccountException {
        Account account = getAccount(authenticationFacade.getCurrentUserName());
        accountService.deposit(account, requestAccountDto.getPinCode(), requestAccountDto.getAmount());
        return ResponseEntity.ok("Счёт пополнен");
    }

    @PutMapping("/withdraw")
    @Operation(summary = "The method of withdrawal of funds from the account")
    public ResponseEntity<String> withdraw(@RequestBody @Valid RequestAccountDto requestAccountDto)
            throws NotEnoughFundsException, WrongPinCodeException, NotFoundUserOrAccountException {
        Account account = getAccount(authenticationFacade.getCurrentUserName());
        accountService.withdraw(account, requestAccountDto.getPinCode(), requestAccountDto.getAmount());
        return ResponseEntity.ok("Деньги с счёта списаны успешно");
    }

    @PutMapping("/transfer")
    @Operation(summary = "Transfer from one account to another")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferDto transferDto)
            throws NotEnoughFundsException, WrongPinCodeException, NotFoundUserOrAccountException {
        Account sender = getAccount(authenticationFacade.getCurrentUserName());
        Account recipient = getAccount(transferDto.getRecipientUsername());
        accountService.transfer(sender, recipient, transferDto.getPinCode(), transferDto.getAmount());
        return ResponseEntity.ok("Перевод выполнен");
    }

    @DeleteMapping("/block-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "The method for the Administrator is to block the account of any user")
    public ResponseEntity<String> blockAccount(@RequestBody @Valid BlockAccountDto blockAccountDto) throws NotFoundUserOrAccountException {
        Account account = getAccount(blockAccountDto.getUsername());
        accountService.blockAccount(account.getId());
        return ResponseEntity.ok("Аккаунт пользователя заблокирован");
    }


    private Account getAccount(String username) throws NotFoundUserOrAccountException {
        User user = userService.getByUsername(username);
        Account account = accountService.getAccountByUser(user);
        if (account == null){
            throw new NotFoundUserOrAccountException("Не найденно аккаунта");
        }
        return account;
    }
}
