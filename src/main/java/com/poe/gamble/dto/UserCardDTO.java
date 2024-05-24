package com.poe.gamble.dto;

import com.poe.gamble.entity.UserCard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCardDTO {
    private Long id;
    private String cardName;
    private Long quantity;

    public static UserCardDTO from(UserCard userCard) {
        return UserCardDTO.builder()
                .id(userCard.getId())
                .cardName(userCard.getCard().getName())
                .quantity(userCard.getQuantity())
                .build();
    }
}
