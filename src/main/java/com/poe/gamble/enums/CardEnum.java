package com.poe.gamble.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum CardEnum {
    UNREQUITED_LOVE("UNREQUITED_LOVE"),
    THE_APOTHECARY("THE_APOTHECARY"),
    HOUSE_OF_MIRRORS("HOUSE_OF_MIRRORS");

    private String name;

    public enum Gambling {
        GAMBLE((quantity) -> {
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity is less than 0");
            }
            return ThreadLocalRandom.current().nextLong(quantity * 2 + 1);
        });

        private final Function<Long, Long> function;

        Gambling(Function<Long, Long> function) {
            this.function = function;
        }

        public Long apply(Long quantity) {
            return function.apply(quantity);
        }
    }

}
