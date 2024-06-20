package com.poe.gamble.exception;

import com.poe.gamble.auth.JwtUtil;
import com.poe.gamble.controller.response.CommonCode;
import com.poe.gamble.controller.response.CommonResponse;
import com.poe.gamble.exception.auth.AccessTokenExpiredException;
import com.poe.gamble.exception.auth.InvalidTokenException;
import com.poe.gamble.exception.auth.RefreshTokenExpiredException;
import com.poe.gamble.exception.auth.TokenNotFoundException;
import com.poe.gamble.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthorizationExceptionHandler {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @ExceptionHandler(AccessTokenExpiredException.class)
    public ResponseEntity<CommonResponse<?>> handleAccessTokenExpiredException(AccessTokenExpiredException e, HttpServletRequest request) {
        final String refreshToken = request.getHeader("RefreshToken");
        if(refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new InvalidTokenException("Refresh Token Not Found");
        }
        String refresh = refreshToken.substring(7);
        try {
            String uuid = jwtUtil.extractUsername(refresh);
            jwtUtil.validateToken(refresh, uuid);
            String dbRefreshToken = userService.getUserToken(UUID.fromString(uuid));
            if(!dbRefreshToken.substring(7).equals(refresh)) {
                throw new InvalidTokenException("Refresh Token Invalid");
            }
            jwtUtil.invalidateToken(refresh);
            JwtUtil.TokenDTO tokenDTO = jwtUtil.generateToken(uuid);
            userService.updateUserToken(UUID.fromString(uuid), tokenDTO.refreshToken());
            HttpHeaders headers = new HttpHeaders();
            headers.set("NEW-ACCESS-TOKEN", tokenDTO.accessToken());
            headers.set("NEW-REFRESH-TOKEN", tokenDTO.refreshToken());
            return new ResponseEntity<>(CommonResponse.fail(CommonCode.TOKEN_RENEW),headers, HttpStatus.OK);
        } catch (ExpiredJwtException ex) {
            return refreshTokenResponse(request,ex);
        } catch (Exception ex) {
            return invalidTokenResponse(request,ex);
        }
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CommonResponse<?>> handleInvalidTokenException(InvalidTokenException e, HttpServletRequest request) {
        return invalidTokenResponse(request,e);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<CommonResponse<?>> handleRefreshTokenExpiredException(RefreshTokenExpiredException e, HttpServletRequest request) {
        return refreshTokenResponse(request,e);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handleTokenNotFoundException(TokenNotFoundException e, HttpServletRequest request) {
        final String requestURL = request.getRequestURI();
        log.error("handleTokenNotFoundException - Request URL : {}, Exception: ",requestURL, e);
        return new ResponseEntity<>(CommonResponse.error(CommonCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<CommonResponse<?>> invalidTokenResponse(HttpServletRequest request,Exception e) {
        final String requestURL = request.getRequestURI();
        log.error("handleBadCredentialsException - Request URL : {}, Exception: ",requestURL, e);
        return new ResponseEntity<>(CommonResponse.error(CommonCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<CommonResponse<?>> refreshTokenResponse(HttpServletRequest request,Exception e) {
        final String requestURL = request.getRequestURI();
        log.error("handleBadCredentialsException - Request URL : {}, Exception: ",requestURL, e);
        return new ResponseEntity<>(CommonResponse.error(CommonCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
}
