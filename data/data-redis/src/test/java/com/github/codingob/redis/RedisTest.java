package com.github.codingob.redis;

import com.github.codingob.redis.model.RedisData;
import com.github.codingob.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;

/**
 * 测试类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Slf4j
@SpringJUnitConfig(locations = "classpath:context.xml")
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    @Test
    void set() {
        redisTemplate.opsForValue().set("redisData", new RedisData(1L,"redis"));
    }

    @Test
    void get(){
        RedisData redisData = (RedisData) redisTemplate.opsForValue().get("redisData");
        System.out.println(redisData);
    }

    @Test
    void transaction(){
        redisService.transactionService();
    }

}
