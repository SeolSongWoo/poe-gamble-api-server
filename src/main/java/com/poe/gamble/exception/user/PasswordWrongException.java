package com.poe.gamble.exception.user;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException(String message) {
        super(message);
    }
}
