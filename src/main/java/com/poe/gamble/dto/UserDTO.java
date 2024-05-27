package com.poe.gamble.dto;

import com.poe.gamble.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String poeName;
    private String email;
    private String password;

    public static UserDTO from(Account account) {
        return UserDTO.builder()
                .id(account.getId())
                .poeName(account.getPoeName())
                .email(account.getEmail())
                .password(account.getPassword())
                .build();
    }

    public static UserDTO of(String poeName, String email,String password) {
        return UserDTO.builder()
                .poeName(poeName)
                .email(email)
                .password(password)
                .build();
    }
}
