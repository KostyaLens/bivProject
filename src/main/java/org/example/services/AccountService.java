package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account){
        accountRepository.save(account);
        return account;
    }

    public Account getAccountByUser(User user){
        return accountRepository.findByUser(user);
    }

    public void upBalance(Account account, String pinCode, long amount){
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void upBalance(Account account, long amount){
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void downBalance(Account account, String pinCode, long amount) throws NotEnoughFundsException {
        if (account.getBalance() < amount){
            throw new NotEnoughFundsException("Недостаточно средсв");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
}
