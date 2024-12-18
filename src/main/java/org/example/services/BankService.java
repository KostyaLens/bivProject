package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.exception.NotFoundBankException;
import org.example.repository.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankService {
    private final BankRepository bankRepository;

    @Transactional
    public void save(Bank bank, Account account) {
        bank.getAccount().add(account);
        bankRepository.save(bank);
    }

    @Transactional
    public void save(Bank bank) {
        bankRepository.save(bank);
    }

    public Bank getBankByName(String name) throws NotFoundBankException {
        return bankRepository.findByName(name).orElseThrow(() -> new NotFoundBankException("Банк не найден"));
    }


    public Optional<Bank> getBankByNameAndByAccount(String nameBank, Account account) {
        return bankRepository.findByNameAndAccount(nameBank, account);
    }

}
