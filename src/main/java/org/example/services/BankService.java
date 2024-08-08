package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.Bank;
import org.example.exception.NotFoundBankException;
import org.example.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;

    public Bank getBankByName(String name) throws NotFoundBankException {
        return bankRepository.findByName(name).orElseThrow(() -> new NotFoundBankException("Банк не найден"));
    }
}
