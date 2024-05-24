package com.poe.gamble.service;

import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.entity.Account;
import com.poe.gamble.exception.user.DuplicateUserException;
import com.poe.gamble.exception.user.UserNotFoundException;
import com.poe.gamble.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDTO getUserByEmail(String email) {
        Account account = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.from(account);
    }

    public void createUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateUserException("User already exists");
        }
        Account account = Account.builder()
                .poeName(userDTO.getPoeName())
                .email(userDTO.getEmail())
                .build();
        userRepository.save(account);
    }
}
