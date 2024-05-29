package com.poe.gamble.repository;

import com.poe.gamble.entity.UserCardInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCardRepository extends JpaRepository<UserCardInventory, Long> {

    @Query("SELECT uc FROM UserCardInventory uc WHERE uc.account.id = :userUUID AND uc.card.id = :cardId")
    Optional<UserCardInventory> findByAccountUUIDAndCardId(@Param("userUUID") UUID userUUID, @Param("cardId") Long cardId);

    @Query("UPDATE UserCardInventory uc SET uc.stockQuantity = :stockQuantity WHERE uc.account.id = :userUUID AND uc.card.id = :cardId")
    void updateUserCardByUUIDAndCardId(@Param("userUUID") UUID userUUID,@Param("cardId") Long cardId,@Param("stockQuantity") Long stockQuantity);

    @Query("SELECT uc FROM UserCardInventory uc WHERE uc.account.id = :userUUID")
    List<UserCardInventory> findByAccountUUID(@Param("userUUID") UUID userUUID);
}
