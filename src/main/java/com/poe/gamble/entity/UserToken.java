package com.poe.gamble.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;

    @Builder
    public UserToken(Long id, String token,Account account) {
        this.id = id;
        this.token = token;
        this.account = account;
    }
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void updateToken(String token) {
        this.token = token;
    }
}
