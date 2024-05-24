package com.poe.gamble.dto;

import com.poe.gamble.entity.Card;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDTO {
    private Long id;
    private String name;
    private Long maxQuantity;


    public static CardDTO from(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .name(card.getName())
                .maxQuantity(card.getMaxQuantity())
                .build();
    }
}
