package com.poe.gamble.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20)
    private String poeName;

    @Column(length = 50)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCardInventory> userCardInventory;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserToken token;

    @Builder
    public Account(UUID id, String poeName, String email, String password) {
        this.id = id;
        this.poeName = poeName;
        this.email = email;
        this.password = password;
    }

    public void updateUserToken(UserToken token) {
        this.token = token;
    }
}
