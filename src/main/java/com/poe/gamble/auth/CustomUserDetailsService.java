package com.poe.gamble.auth;

import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO userDTO = userService.getUserByEmail(email);

        return User.withUsername(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles("USER")
                .build();
    }
}
