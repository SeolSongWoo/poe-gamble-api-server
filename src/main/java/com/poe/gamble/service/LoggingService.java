package com.poe.gamble.service;

import com.poe.gamble.enums.LoggingType;
import com.poe.gamble.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoggingService {
    private final RedisUtil redisUtil;

    public void gambleLogging(UUID userUUID, Long result) {
        String userHash = "user:" + userUUID.toString() + ":gamble";
        redisUtil.increment(userHash, "attempt", 1);
        redisUtil.increment(userHash, "cards_result", result.intValue());
    }
}
