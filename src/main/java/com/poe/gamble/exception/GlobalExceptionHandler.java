package com.poe.gamble.exception;

import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.exception.card.CardGambleException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CardGambleException.class)
    public ResponseEntity<CommonResponse<?>> handleCardGambleException(CardGambleException e, HttpServletRequest request) {
        final String requestURL = request.getRequestURI();
        log.error("handleCardGambleException - Request URL : {}, Exception: ",requestURL, e);
        return new ResponseEntity<>(CommonResponse.error(CommonCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse<?>> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        final String requestURL = request.getRequestURI();
        log.error("handleBadCredentialsException - Request URL : {}, Exception: ",requestURL, e);
        return new ResponseEntity<>(CommonResponse.error(CommonCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleDefaultException(Exception e, HttpServletRequest request) {
        final String requestURL = request.getRequestURI();
        log.error("handleException - Request URL : {}, Exception: ",requestURL, e);
        return new ResponseEntity<>(CommonResponse.error(CommonCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
