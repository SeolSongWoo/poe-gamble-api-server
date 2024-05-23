package com.poe.gamble.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonCode {

    // Success codes
    INSERT_OK(201, "Insertion successful"),
    UPDATE_OK(204, "Update successful"),
    DELETE_OK(204, "Deletion successful"),
    CREATE_OK(201, "Creation successful"),
    FOUND_OK(200, "Resource found"),
    REQUEST_OK(200, "Request successful"),
    LOGIN_OK(200,"Login successful"),

    // Client error codes
    BAD_REQUEST(400, "Bad request"),
    PAGE_NOT_FOUND(404, "Page not found"),
    USER_NOT_FOUND(404, "User not found"),
    FORBIDDEN(403, "Forbidden"),
    DUPLICATE(409,"Duplicate Occurrence"),


    // Server error codes
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private final int status;
    private final String message;
}