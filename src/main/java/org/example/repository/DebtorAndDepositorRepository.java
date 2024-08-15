package org.example.repository;

import org.example.entity.Account;
import org.example.entity.DebtorAndDepositor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DebtorAndDepositorRepository extends JpaRepository<DebtorAndDepositor, Long> {
    List<DebtorAndDepositor> findDebtorAndDepositorByPaymentDeadlineIsBefore(LocalDateTime now);
    Optional<DebtorAndDepositor> findDebtorAndDepositorByAccount(Account account);
}
