package com.poe.gamble.repository;

import com.poe.gamble.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findCardByName(String cardName);
}
