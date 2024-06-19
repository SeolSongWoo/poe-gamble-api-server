package com.poe.gamble.auth;


import com.poe.gamble.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final RedisUtil redisUtil;
    private final static String secretKey = "mysecrettestkey12345mysecrettestkey12345mysecrettestkey12345";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public void invalidateToken(String token) {
        redisUtil.set("user:expired:token",token);
    }

    // 토큰이 블랙리스트에 있는지 확인하는 메소드
    private Boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisUtil.isValue("user:expired:token",token));
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public TokenDTO generateToken(String username) {
        String accessToken = createToken(username, System.currentTimeMillis() + 1000 * 10);
        String refreshToken = createToken(username, System.currentTimeMillis() + 1000 * 60 * 60 * 10);
        return new TokenDTO("Bearer " + accessToken, "Bearer " + refreshToken);
    }

    private String createToken(String subject, long expiration) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    public record TokenDTO(String accessToken, String refreshToken){}
}
