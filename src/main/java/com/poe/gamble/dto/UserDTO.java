package com.poe.gamble.dto;

import com.poe.gamble.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID id;
    private String poeName;
    private String email;

    public static UserDTO of(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .poeName(user.getPoeName())
                .email(user.getEmail())
                .build();
    }
}
