package com.poe.gamble.auth;

import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO;
        if(isUUID(username)) {
            userDTO = userService.getUserByUUID(UUID.fromString(username));
        }else {
            userDTO = userService.getUserByEmail(username);
        }

        return User.withUsername(userDTO.getId().toString())
                .password(userDTO.getPassword())
                .roles("USER")
                .build();
    }

    private boolean isUUID(String username) {
        if(username == null) return false;

        try {
            UUID.fromString(username);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
