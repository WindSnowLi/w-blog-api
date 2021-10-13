package com.hiyj.blog.caches.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisConfigTest {
    @Autowired
    private RedisConfig redisConfig;

    @Test
    public void testRedisConfigTest(){
        System.out.println(redisConfig);
    }
}
