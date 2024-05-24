package com.poe.gamble.repository;

import com.poe.gamble.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {

    @Query("SELECT uc FROM UserCard uc WHERE uc.account.id = :userUUID AND uc.card.id = :cardId")
    Optional<UserCard> findByAccountUUIDAndCardId(UUID userUUID, Long cardId);
}
