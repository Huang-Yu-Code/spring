package com.demo.spring.data.redis;

import com.demo.spring.data.redis.config.JedisConfig;
import com.demo.spring.data.redis.config.LettuceConfig;
import com.demo.spring.data.redis.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Slf4j
@SpringJUnitConfig(LettuceConfig.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void stringSet() {
        stringRedisTemplate.opsForValue().set("name", "中文");
    }

    @Test
    void stringGet() {
        String value = stringRedisTemplate.opsForValue().get("name");
        log.info(value);
    }

    @Test
    void hashSet() {
        Entity entity = new Entity(1L, "user1", false);
        redisTemplate.boundHashOps("entity").put(entity.getId().toString(), entity);
    }

    @Test
    void hashGet() {
        Entity entity = (Entity) redisTemplate.boundHashOps("entity").get("1");
        Assertions.assertNotNull(entity);
        log.info(entity.getName());
    }
}
