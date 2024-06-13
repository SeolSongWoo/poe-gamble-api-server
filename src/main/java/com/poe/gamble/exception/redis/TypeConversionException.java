package com.poe.gamble.exception.redis;

public class TypeConversionException extends RuntimeException{
    public TypeConversionException(String message) {
        super(message);
    }

    public TypeConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
