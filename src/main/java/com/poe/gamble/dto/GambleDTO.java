package com.poe.gamble.dto;

import lombok.Builder;
import lombok.Data;

public class GambleDTO {
    @Data
    @Builder
    public static class Request {
        private String cardName;
        private Long stockQuantity;
    }

    @Data
    @Builder
    public static class Response {
        private String cardName;
        private Long tryQuantity;
        private Long stockQuantity;
    }
}
