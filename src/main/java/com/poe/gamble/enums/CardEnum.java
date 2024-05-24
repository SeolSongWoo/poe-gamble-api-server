package com.poe.gamble.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum CardEnum {
    UNREQUITED_LOVE("UNREQUITED_LOVE"),
    THE_APOTHECARY("The_APOTHECARY"),
    HOUSE_OF_MIRRORS("HOUSE_OF_MIRRORS");

    private String name;

    public enum Gambling {
        GAMBLE( (quantity) -> {
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity is less than 0");
            }
            Random random = new Random();
            return random.nextLong(quantity * 2 + 1);
        });

        private Function<Long, Long> function;

        Gambling(Function<Long, Long> function) {
            this.function = function;
        }

        public Long apply(Long quantity) {
            return function.apply(quantity);
        }
    }

}
