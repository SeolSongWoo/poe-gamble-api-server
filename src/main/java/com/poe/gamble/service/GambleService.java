package com.poe.gamble.service;

import com.poe.gamble.dto.CardDTO;
import com.poe.gamble.dto.GambleDTO;
import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.enums.CardEnum;
import com.poe.gamble.exception.card.CardGambleException;
import com.poe.gamble.exception.card.CardStockNotEnoughException;
import com.poe.gamble.exception.card.CardStockTooMuchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GambleService {
    private final CardService cardService;
    private final UserCardService userCardService;
    private final LoggingService loggingService;
    public GambleDTO.Response tryGambling(UUID userUUID,GambleDTO.Request request) {
        CardDTO cardDTO = cardService.getCardByName(request.getCardName());
        UserCardDTO userCard = userCardService.getAccountCardByUUIDAndCardId(userUUID, cardDTO.getId());

        checkStock(request.getTryQuantity(), cardDTO.getMaxQuantity(), userCard.getStockQuantity());

        Long resultQuantity = CardEnum.Gambling.GAMBLE.apply(request.getTryQuantity());

        Long userStockQuantity = resultQuantity - request.getTryQuantity() + userCard.getStockQuantity();

        userCardService.updateStockQuantity(userUUID, cardDTO.getId(), userStockQuantity);

        Long differenceQuantity = resultQuantity - request.getTryQuantity();

        loggingService.gambleLogging(userUUID, differenceQuantity);

        return GambleDTO.Response.builder()
                .cardName(cardDTO.getName())
                .tryQuantity(request.getTryQuantity())
                .stockQuantity(resultQuantity)
                .userCardStockQuantity(userStockQuantity)
                .build();
    }

    private void checkStock(Long gambleQuantity, Long maxQuantity, Long stockQuantity) throws CardGambleException {
        if(gambleQuantity * 2 > maxQuantity) {
            throw new CardStockTooMuchException("To much Stock");
        }
        if(gambleQuantity > stockQuantity || gambleQuantity < 0) {
            throw new CardStockNotEnoughException("Not enough Stock");
        }
    }
}
