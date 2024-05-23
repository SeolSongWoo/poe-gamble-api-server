package com.poe.gamble.service;

import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.entity.User;
import com.poe.gamble.exception.user.UserNotFoundException;
import com.poe.gamble.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.of(user);
    }
}
