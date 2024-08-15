package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.Debtor;
import org.example.repository.DebtorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebtorService {
    private final DebtorRepository debtorRepository;

    public void save(Debtor debtor) {
        debtorRepository.save(debtor);
    }
}
