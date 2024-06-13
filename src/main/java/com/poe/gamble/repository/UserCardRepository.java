package com.poe.gamble.repository;

import com.poe.gamble.entity.UserCardInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCardRepository extends JpaRepository<UserCardInventory, Long> {
    Optional<UserCardInventory> findUserCardInventoriesByAccountIdAndCardId(@Param("accountId") UUID userUUID, @Param("cardId") Long cardId);
    List<UserCardInventory> findByAccountId(@Param("accountId") UUID userUUID);
}
