package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.Bank;
import org.example.exception.NotFoundBankException;
import org.example.repository.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankService {
    private final BankRepository bankRepository;

    @Transactional(readOnly = true)
    public Bank getBankByName(String name) throws NotFoundBankException {
        return bankRepository.findByName(name).orElseThrow(() -> new NotFoundBankException("Банк не найден"));
    }
}
