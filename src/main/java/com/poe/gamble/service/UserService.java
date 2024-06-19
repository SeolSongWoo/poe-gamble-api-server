package com.poe.gamble.service;

import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.dto.UserDTO;
import com.poe.gamble.entity.Account;
import com.poe.gamble.entity.UserToken;
import com.poe.gamble.exception.user.DuplicateUserException;
import com.poe.gamble.exception.user.UserNotFoundException;
import com.poe.gamble.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUserByEmail(String email) {
        Account account = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.from(account);
    }

    public void createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateUserException("User already exists");
        }
        Account account = Account.builder()
                .poeName(userDTO.getPoeName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(account);
    }

    public UserDTO getUserByUUID(UUID uuid) {
        Account account = userRepository.findAccountById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.from(account);
    }

    @Transactional
    public void saveUserToken(UUID uuid, String token) {
        Account account = userRepository.findAccountById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserToken userToken = UserToken.builder()
                .token(token)
                .account(account)
                .build();
        account.updateToken(userToken);
    }

    public String getUserToken(UUID uuid) {
        Account account = userRepository.findAccountById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return account.getToken().getToken();
    }

    @Transactional
    public void updateUserToken(UUID uuid, String token) {
        Account account = userRepository.findAccountById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        account.getToken().updateToken(token);
    }
}
