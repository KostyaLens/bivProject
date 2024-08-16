package org.example.services;

import liquibase.exception.ServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.DebtorAndDepositor;
import org.example.entity.TypeBankAmenities;
import org.example.repository.DebtorAndDepositorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebtorAndDepositorService {
    private final DebtorAndDepositorRepository debtorAndDepositorRepository;

    public void save(DebtorAndDepositor debtorAndDepositor) {
        debtorAndDepositorRepository.save(debtorAndDepositor);
    }

    public List<DebtorAndDepositor> getDebtorAndDepositorAfterDeadline(LocalDateTime now) {
        return debtorAndDepositorRepository.findDebtorAndDepositorByPaymentDeadlineIsBefore(now);
    }

    @Transactional
    public void delete(DebtorAndDepositor debtorAndDepositor) {
        debtorAndDepositorRepository.delete(debtorAndDepositor);
    }

    public DebtorAndDepositor getDebtor(Account account) {
        return debtorAndDepositorRepository.findDebtorAndDepositorByAccount(account).orElseThrow(() -> new ServiceNotFoundException("У данного аккаунта нет задолжности"));
    }

    public List<DebtorAndDepositor> getDebtorAndDepositorByTypeBankAmenoties(int page, int count, TypeBankAmenities typeBankAmenities) {
        return debtorAndDepositorRepository.findDebtorAndDepositorByType(typeBankAmenities, PageRequest.of(page, count)).getContent();
    }
}
