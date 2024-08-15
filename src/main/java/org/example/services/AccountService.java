package org.example.services;

import org.example.entity.*;
import org.example.exception.WrongPinCodeException;
import org.example.mappers.DebtorMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.exception.NotEnoughFundsException;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final BankAmenitiesService bankAmenitiesService;

    private final DebtorMapper debtorMapper;

    private final DebtorService debtorService;

    @Transactional
    public Account createAccount(Account account, User user, Bank bank){
        account.setActive(true);
        account.setBank(bank);
        account.setUser(user);
        accountRepository.save(account);
        return account;
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountByUserAndBank(User user, Bank bank){
        return accountRepository.findByUserAndBank(user, bank);
    }

    @Transactional
    public void deposit(Account account, long amount){
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(Account account, String pinCode, long amount) throws NotEnoughFundsException, WrongPinCodeException {
        checkPinCode(account, pinCode);
        if (account.getBalance() < amount){
            throw new NotEnoughFundsException("Недостаточно средсв");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Account sender, Account recipient, String pinCode, long amount) throws NotEnoughFundsException, WrongPinCodeException {
        withdraw(sender, pinCode, amount);
        deposit(recipient, amount);
    }

    @Transactional
    public void blockAccount(long idAccount){
        accountRepository.deleteById(idAccount);
    }

    public void checkPinCode(Account account, String pinCode) throws WrongPinCodeException {
        if (!pinCode.equals(account.getPinCode())){
            throw new WrongPinCodeException("Неверный пин-код");
        }
    }

    @Transactional
    public void issuingCredit(Account account, String nameBank, int serviceNumber, long amount, String pinCode) throws WrongPinCodeException {
        checkPinCode(account, pinCode);
        BankAmenities bankAmenities = bankAmenitiesService.getBankAmenitiesByNameBankAndServiceNumber(nameBank, serviceNumber);
        deposit(account, amount);
        Debtor debtor = debtorMapper.toDebtor(account, bankAmenities);
        long debt = amount + amount*bankAmenities.getPercent();
        debtor.setTotalDebt(debt);
        debtorService.save(debtor);
        System.out.println(debtor);
    }
}
