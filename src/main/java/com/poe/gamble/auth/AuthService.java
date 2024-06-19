package com.poe.gamble.auth;

import com.poe.gamble.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public JwtUtil.TokenDTO getJwtToken(AuthenticationRequestDTO authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final JwtUtil.TokenDTO tokenDTO = jwtUtil.generateToken(userDetails.getUsername());
        userService.saveUserToken(UUID.fromString(userDetails.getUsername()), tokenDTO.refreshToken());
        return tokenDTO;

    }
}
