package com.poe.gamble.repository;

import com.poe.gamble.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findUserByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Account> findAccountById(UUID uuid);
}
