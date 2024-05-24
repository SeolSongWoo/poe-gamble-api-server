package com.poe.gamble.service;

import com.poe.gamble.dto.CardDTO;
import com.poe.gamble.dto.GambleDTO;
import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.enums.CardEnum;
import com.poe.gamble.exception.user.CardGambleException;
import com.poe.gamble.exception.user.CardStockNotEnoughException;
import com.poe.gamble.exception.user.CardStockTooMuchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GambleService {
    private final CardService cardService;
    private final UserCardService userCardService;
    public GambleDTO.Response tryGambling(UUID userUUID,GambleDTO.Request request) {
        CardDTO card = cardService.getCardByName(request.getCardName());
        UserCardDTO userCard = userCardService.getAccountCardByUUIDAndCardId(userUUID, card.getId());

        checkStock(request.getStockQuantity(), card.getMaxQuantity(), userCard.getQuantity());

        Long resultQuantity = CardEnum.Gambling.GAMBLE.apply(request.getStockQuantity());
    }

    private void checkStock(Long gambleQuantity, Long maxQuantity, Long stockQuantity) throws CardGambleException {
        if(gambleQuantity * 2 > maxQuantity) {
            throw new CardStockTooMuchException("To much Stock");
        }
        if(gambleQuantity > stockQuantity) {
            throw new CardStockNotEnoughException("Not enough Stock");
        }
    }
}
