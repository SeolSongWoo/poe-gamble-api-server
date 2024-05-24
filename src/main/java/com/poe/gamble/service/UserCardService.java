package com.poe.gamble.service;

import com.poe.gamble.dto.CardDTO;
import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.entity.UserCard;
import com.poe.gamble.exception.user.UserCardNotFoundException;
import com.poe.gamble.repository.UserCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCardService {
    private final UserCardRepository userCardRepository;

    public UserCardDTO getAccountCardByUUIDAndCardId(UUID userUUID, Long cardId) {
        UserCard userCard = userCardRepository.findByAccountUUIDAndCardId(userUUID, cardId)
                .orElseThrow(() -> new UserCardNotFoundException("User card not found"));

        return UserCardDTO.from(userCard);
    }
}
