package com.poe.gamble.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenExpiredException extends AuthenticationException {
    public AccessTokenExpiredException(String message) {
        super(message);
    }
}
