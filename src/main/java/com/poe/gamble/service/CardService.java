package com.poe.gamble.service;

import com.poe.gamble.dto.CardDTO;
import com.poe.gamble.entity.Card;
import com.poe.gamble.exception.card.CardNotFoundException;
import com.poe.gamble.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public CardDTO getCardByName(String cardName) {
        Card card = cardRepository.findCardByName(cardName)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));
        return CardDTO.from(card);
    }
}
