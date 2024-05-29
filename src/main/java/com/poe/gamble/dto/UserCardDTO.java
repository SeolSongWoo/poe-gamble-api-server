package com.poe.gamble.dto;

import com.poe.gamble.entity.UserCardInventory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserCardDTO {
    private Long id;
    private String cardName;
    private Long stockQuantity;

    public static UserCardDTO from(UserCardInventory userCardInventory) {
        return UserCardDTO.builder()
                .id(userCardInventory.getId())
                .cardName(userCardInventory.getCard().getName())
                .stockQuantity(userCardInventory.getStockQuantity())
                .build();
    }

    public static List<UserCardDTO> fromList(List<UserCardInventory> userCardInventory) {
        return userCardInventory.stream()
                .map(UserCardDTO::from)
                .toList();
    }
}
