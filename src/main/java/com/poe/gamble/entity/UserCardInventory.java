package com.poe.gamble.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserCardInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long stockQuantity;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void updateStockQuantity(Long userStockQuantity) {
        this.stockQuantity = userStockQuantity;
    }
}
