package com.poe.gamble.service;

import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.entity.UserCardInventory;
import com.poe.gamble.exception.user.UserCardNotFoundException;
import com.poe.gamble.repository.UserCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCardService {
    private final UserCardRepository userCardRepository;

    public UserCardDTO getAccountCardByUUIDAndCardId(UUID userUUID, Long cardId) {
        UserCardInventory userCardInventory = userCardRepository.findUserCardInventoriesByAccountIdAndCardId(userUUID, cardId)
                .orElseThrow(() -> new UserCardNotFoundException("User card not found"));

        return UserCardDTO.from(userCardInventory);
    }

    @Transactional
    public void updateStockQuantity(UUID userUUID, Long cardId, Long userStockQuantity) {
        UserCardInventory userCardInventory = userCardRepository.findUserCardInventoriesByAccountIdAndCardId(userUUID, cardId)
                .orElseThrow(() -> new UserCardNotFoundException("User card not found"));
        userCardInventory.updateStockQuantity(userStockQuantity);
    }

    public List<UserCardDTO> getUserCardsByUUID(UUID userUUID) {
        List<UserCardInventory> userCardInventory = userCardRepository.findByAccountId(userUUID);
        return UserCardDTO.fromList(userCardInventory);
    }
}
