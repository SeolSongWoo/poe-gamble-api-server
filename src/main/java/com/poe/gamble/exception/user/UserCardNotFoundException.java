package com.poe.gamble.exception.user;

public class UserCardNotFoundException extends RuntimeException{

    public UserCardNotFoundException(String message) {
        super(message);
    }
}
