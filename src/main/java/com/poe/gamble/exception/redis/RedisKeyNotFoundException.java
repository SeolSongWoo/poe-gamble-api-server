package com.poe.gamble.exception.redis;

public class RedisKeyNotFoundException extends RuntimeException{
    public RedisKeyNotFoundException(String message) {
        super(message);
    }

    public RedisKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
