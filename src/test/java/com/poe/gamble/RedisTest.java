package com.poe.gamble;

import com.poe.gamble.util.RedisUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;
    @BeforeEach
    public void setup() {
        redisUtil.set("user:test:gamble", "attempt", "0");
        redisUtil.set("user:test:gamble", "data", "hello-world");
    }

    @Test
    public void getTest() {
        String data = redisUtil.get("user:test:gamble","data");
        Assertions.assertEquals("hello-world", data);
    }


    @Test
    public void incrementTest() {
        redisUtil.set("user:test:gamble", "attempt", "0");
        redisUtil.increment("user:test:gamble", "attempt", 1);
        Assertions.assertEquals("1", redisUtil.get("user:test:gamble", "attempt"));
        redisUtil.increment("user:test:gamble", "attempt", 4);
        Assertions.assertEquals("5", redisUtil.get("user:test:gamble", "attempt"));
    }

}
