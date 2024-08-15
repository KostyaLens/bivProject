package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserAndBank(User user, Bank bank);
}
