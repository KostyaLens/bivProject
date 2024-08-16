package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserAndBank(User user, Bank bank);
}
