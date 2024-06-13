package com.poe.gamble.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poe.gamble.exception.redis.RedisKeyNotFoundException;
import com.poe.gamble.exception.redis.TypeConversionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisUtil {


    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String,Object> get(String key) {
        HashOperations<String, String, Object> redisHashOperationsByString = redisTemplate.opsForHash();
        return redisHashOperationsByString.entries(key);
    }

    public String get(String hash,String hashKey) throws RedisKeyNotFoundException,TypeConversionException {
        HashOperations<String, String, String> redisHashOperationsByString = redisTemplate.opsForHash();
        return redisHashOperationsByString.get(hash,hashKey);
    }

    public void increment(String hash, String hashKey, int value) {
        HashOperations<String, String, String> redisHashOperationsByString = redisTemplate.opsForHash();
        redisHashOperationsByString.increment(hash,hashKey,value);
    }

    public void set(String hash,String hashKey, String value) {
        HashOperations<String, String, String> redisHashOperationsByString = redisTemplate.opsForHash();
        redisHashOperationsByString.put(hash,hashKey,value);
    }

    public void destroy(String hash) {
        redisTemplate.delete(hash);
    }
    public void destroy(String hash,String hashKey) {
        HashOperations<String, String, String> redisHashOperationsByString = redisTemplate.opsForHash();
        redisHashOperationsByString.delete(hash,hashKey);
    }
}
