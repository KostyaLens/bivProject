package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.exception.NotEnoughFundsException;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account){
        accountRepository.save(account);
        return account;
    }

    public Optional<Account> getAccountById(long id){
        return accountRepository.findById(id);
    }

    public void upBalance(long id, long amount){
        Account account = accountRepository.findById(id).orElseThrow();
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void downBalance(long id, long amount){
        Account account = accountRepository.findById(id).orElseThrow();
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void deleteById(long id){
        accountRepository.deleteById(id);
    }
}
