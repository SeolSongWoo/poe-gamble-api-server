package com.poe.gamble.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class GambleDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String cardName;
        private Long tryQuantity;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String cardName;
        private Long tryQuantity;
        private Long stockQuantity;
        private Long userCardStockQuantity;
    }
}
