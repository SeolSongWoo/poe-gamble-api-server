package com.poe.gamble;

import com.poe.gamble.dto.CardDTO;
import com.poe.gamble.dto.GambleDTO;
import com.poe.gamble.dto.UserCardDTO;
import com.poe.gamble.exception.card.CardStockNotEnoughException;
import com.poe.gamble.exception.card.CardStockTooMuchException;
import com.poe.gamble.service.CardService;
import com.poe.gamble.service.GambleService;
import com.poe.gamble.service.UserCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GambleServiceTest {

    @Mock
    private CardService cardService;
    @Mock
    private UserCardService userCardService;
    @InjectMocks
    private GambleService gambleService;
    private UUID userUUID;
    private GambleDTO.Request request;
    private CardDTO cardDTO;
    private UserCardDTO userCardDTO;

    @BeforeEach
    public void setup() {
        userUUID = UUID.randomUUID();
        request = new GambleDTO.Request("짝사랑", 5L);

        cardDTO = CardDTO.builder()
                .id(1L)
                .name("짝사랑")
                .maxQuantity(10L)
                .build();

        userCardDTO = UserCardDTO.builder()
                .stockQuantity(1000L)
                .build();
    }

    @Test
    public void 겜블링_이벤트_성공() {
        System.out.println(UUID.randomUUID());
        when(cardService.getCardByName(anyString())).thenReturn(cardDTO);
        when(userCardService.getAccountCardByUUIDAndCardId(any(UUID.class), anyLong())).thenReturn(userCardDTO);

        System.out.println("try Quantity : " + request.getTryQuantity());
        for (int i = 0; i < 100; i++) {
            GambleDTO.Response response = gambleService.tryGambling(userUUID, request);
            Long resultQuantity = response.getStockQuantity();

            System.out.println("Gambling result " + (i + 1) + ": " + resultQuantity);

            assertNotNull(response);
            assertEquals("짝사랑", response.getCardName());
            assertEquals(5L, response.getTryQuantity());
            assertTrue(resultQuantity >= 0 && resultQuantity <= request.getTryQuantity() * 2,
                    "Result quantity should be between 0 and " + (request.getTryQuantity() * 2));
            userCardDTO.setStockQuantity(resultQuantity - request.getTryQuantity() + userCardDTO.getStockQuantity());
            System.out.println( "Stock Quantity : "+userCardDTO.getStockQuantity());
        }
    }

    @Test
    public void 겜블링_이벤트_겜블시도갯수_초과_예외() {
        request = new GambleDTO.Request("CardName", 6L);

        when(cardService.getCardByName(anyString())).thenReturn(cardDTO);
        when(userCardService.getAccountCardByUUIDAndCardId(any(UUID.class), anyLong())).thenReturn(userCardDTO);

        Exception exception = assertThrows(CardStockTooMuchException.class, () -> {
            gambleService.tryGambling(userUUID, request);
        });

        assertEquals("To much Stock", exception.getMessage());
    }

    @Test
    public void 겜블링_이벤트_카드갯수_부족_예외() {
        userCardDTO.setStockQuantity(4L);

        when(cardService.getCardByName(anyString())).thenReturn(cardDTO);
        when(userCardService.getAccountCardByUUIDAndCardId(any(UUID.class), anyLong())).thenReturn(userCardDTO);

        Exception exception = assertThrows(CardStockNotEnoughException.class, () -> {
            gambleService.tryGambling(userUUID, request);
        });

        assertEquals("Not enough Stock", exception.getMessage());
    }
}
