package com.poe.gamble.auth;

import com.poe.gamble.exception.auth.AccessTokenExpiredException;
import com.poe.gamble.exception.auth.InvalidTokenException;
import com.poe.gamble.exception.auth.TokenNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String accessToken = request.getHeader("Authorization");
            String access = null;

            if (accessToken == null || !accessToken.startsWith("Bearer ")) {
                throw new TokenNotFoundException("Token Not Found");
            }

            access = accessToken.substring(7).trim();

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                validateAccessToken(access, request);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    private void validateAccessToken(String accessToken, HttpServletRequest request) throws AccessTokenExpiredException, InvalidTokenException {
        try {
            UUID uuid = UUID.fromString(jwtUtil.extractUsername(accessToken));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(uuid.toString());
            jwtUtil.validateToken(accessToken, userDetails.getUsername());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (ExpiredJwtException e) {
            throw new AccessTokenExpiredException("Access Token Expired");
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid Access Token");
        }
    }
}
