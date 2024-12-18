package org.example.repository;

import org.example.entity.Account;
import org.example.entity.DebtorAndDepositor;
import org.example.entity.TypeBankAmenities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebtorAndDepositorRepository extends JpaRepository<DebtorAndDepositor, Long> {
    List<DebtorAndDepositor> findDebtorAndDepositorByPaymentDeadlineIsBefore(LocalDateTime now);

    Optional<DebtorAndDepositor> findDebtorAndDepositorByAccount(Account account);

    Page<DebtorAndDepositor> findDebtorAndDepositorByType(TypeBankAmenities typeBankAmenities, Pageable pageable);
}
