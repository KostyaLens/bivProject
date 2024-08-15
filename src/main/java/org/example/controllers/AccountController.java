package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.example.dto.*;
import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.BankAmenities;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.exception.NotFoundBankException;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.exception.WrongPinCodeException;
import org.example.mappers.AccountMapper;
import org.example.mappers.BankAmenitiesMapper;
import org.example.security.AuthenticationFacade;
import org.example.services.AccountService;
import org.example.services.BankAmenitiesService;
import org.example.services.BankService;
import org.example.services.UserService;
import org.example.validatros.PinCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    private final BankAmenitiesService bankAmenitiesService;

    private final BankAmenitiesMapper bankAmenitiesMapper;

    @PostMapping("/created")
    @Operation(summary = "Creating an account")
    public AccountDto createAccount(@Valid @RequestBody CreateAccountDto createAccountDto)
            throws NotFoundUserOrAccountException, NotFoundBankException {
        User user = userService.getByUsername(authenticationFacade.getCurrentUserName());
        Account account = accountMapper.toEntity(createAccountDto);
        Bank bank = bankService.getBankByName(createAccountDto.getBank());
        bankService.save(bank, account);
        accountService.createAccount(account, user, bank);
        return accountMapper.toDto(account);
    }

    @GetMapping("/info")
    @Operation(summary = "Account Information")
    public AccountDto infoAccount(@RequestParam @PinCode @NotEmpty(message = "Не введён пин-код") String pinCode,
                                  @RequestParam @Schema(name = "bank", example = "Т-Банк") @NotEmpty(message = "Не указан банк") String bank)
                                        throws WrongPinCodeException, NotFoundUserOrAccountException, NotFoundBankException {
        Account account = getAccount(authenticationFacade.getCurrentUserName(), bank);
        accountService.checkPinCode(account, pinCode);
        return accountMapper.toDto(account);
    }

    @PutMapping("/admin-assign-founds-to-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "The method of replenishment of the account")
     public ResponseEntity<String> depositAnyAccount(@RequestBody @Valid RequestAccountDto requestAccountDto)
            throws WrongPinCodeException, NotFoundUserOrAccountException, NotFoundBankException {
        Account recipient = getAccount(requestAccountDto.getRecipientUsername(), requestAccountDto.getBank());
        accountService.deposit(recipient, requestAccountDto.getAmount());
        return ResponseEntity.ok("Счёт пополнен");
    }

    @PutMapping("/withdraw")
    @Operation(summary = "The method of withdrawal of funds from the account")
    public ResponseEntity<String> withdraw(@RequestBody @Valid RequestAccountDto requestAccountDto)
            throws NotEnoughFundsException, WrongPinCodeException, NotFoundUserOrAccountException, NotFoundBankException {
        Account account = getAccount(authenticationFacade.getCurrentUserName(), requestAccountDto.getBank());
        accountService.withdraw(account, requestAccountDto.getPinCode(), requestAccountDto.getAmount());
        return ResponseEntity.ok("Деньги с счёта списаны успешно");
    }

    @PutMapping("/transfer")
    @Operation(summary = "Transfer from one account to another")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferDto transferDto)
            throws NotEnoughFundsException, WrongPinCodeException, NotFoundUserOrAccountException, NotFoundBankException {
        Account sender = getAccount(authenticationFacade.getCurrentUserName(), transferDto.getSenderBank());
        Account recipient = getAccount(transferDto.getRecipientUsername(), transferDto.getRecipientBank());
        accountService.transfer(sender, recipient, transferDto.getPinCode(), transferDto.getAmount());
        return ResponseEntity.ok("Перевод выполнен");
    }

    @DeleteMapping("/block-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "The method for the Administrator is to block the account of any user")
    public ResponseEntity<String> blockAccount(@RequestBody @Valid BlockAccountDto blockAccountDto)
            throws NotFoundUserOrAccountException, NotFoundBankException {
        Account account = getAccount(blockAccountDto.getUsername(), blockAccountDto.getBank());
        accountService.blockAccount(account.getId());
        return ResponseEntity.ok("Аккаунт пользователя заблокирован");
    }


    @GetMapping("/info-amenities")
    public List<ResponseBankAmenities> infoAmenities(@RequestParam @NotEmpty(message = "Введите название банка") String nameBank,
                                                     @RequestParam @Min(value = 0, message = "Не указана страница") int page,
                                                     @RequestParam @Min(value = 0, message = "Не указано количество элементов на странице") int count,
                                                     @RequestParam @NotEmpty(message = "Не выбран тип услуги") String typeAmenities,
                                                     @RequestParam(required = false) List<String> sortingDirection){
        List<BankAmenities> bankAmenitiesList = bankAmenitiesService.
                bankAmenitiesByTypeAndBankName(nameBank, page, count, typeAmenities, sortingDirection).getContent();
        return bankAmenitiesMapper.toDtoList(bankAmenitiesList);
    }

    @PutMapping("/make-deposit")
    public void makeDeposit(@RequestBody @Valid CreditDto creditDto)
            throws NotFoundBankException, NotFoundUserOrAccountException, WrongPinCodeException, NotEnoughFundsException {
        Account account = getAccount(authenticationFacade.getCurrentUserName(), creditDto.getNameBank());
        if(account.getBalance() < creditDto.getAmount()){
            throw new NotEnoughFundsException("Недостаточно средств");
        }
        accountService.makeDeposit(account, creditDto.getNameBank(), creditDto.getServiceNumber(), creditDto.getAmount(), creditDto.getPinCode());
    }

    @PutMapping("/take-the-credit")
    public void takeCredit(@RequestBody @Valid CreditDto creditDto)
            throws NotFoundBankException, NotFoundUserOrAccountException, WrongPinCodeException {
        Account account = getAccount(authenticationFacade.getCurrentUserName(), creditDto.getNameBank());
        accountService.issuingCredit(account, creditDto.getNameBank(), creditDto.getServiceNumber(), creditDto.getAmount(), creditDto.getPinCode());
    }


    @PutMapping("/repay-debt")
    public void repayDebt(@RequestBody @Valid RepayDebtDto repayDebtDto)
            throws NotFoundBankException, NotFoundUserOrAccountException, WrongPinCodeException {
        Account account = getAccount(authenticationFacade.getCurrentUserName(), repayDebtDto.getNameBank());
        accountService.repayDebt(account, repayDebtDto.getAmount(), repayDebtDto.getPinCode());
    }


    private Account getAccount(String username, String bankName) throws NotFoundUserOrAccountException, NotFoundBankException {
        User user = userService.getByUsername(username);
        return accountService.getAccountByUserAndBank(user, bankService.getBankByName(bankName))
                .orElseThrow(() -> new NotFoundUserOrAccountException("Не найденно аккаунта"));
    }


}
