package com.poe.gamble.dto;

import com.poe.gamble.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private UUID id;
    private String poeName;
    private String email;

    public static UserDTO from(Account account) {
        return UserDTO.builder()
                .id(account.getId())
                .poeName(account.getPoeName())
                .email(account.getEmail())
                .build();
    }

    public static UserDTO of(String poeName, String email) {
        return UserDTO.builder()
                .poeName(poeName)
                .email(email)
                .build();
    }
}
