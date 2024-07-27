package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
}
