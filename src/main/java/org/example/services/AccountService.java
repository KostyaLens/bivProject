package org.example.services;

import org.example.exception.NotFoundUserOrAccountException;
import org.example.exception.WrongPinCodeException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Account account){
        accountRepository.save(account);
        return account;
    }

    @Transactional(readOnly = true)
    public Account getAccountByUser(User user){
        return accountRepository.findByUser(user);
    }

    @Transactional
    public void deposit(Account account, String pinCode, long amount) throws WrongPinCodeException {
        checkPinCode(account, pinCode);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
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
}
