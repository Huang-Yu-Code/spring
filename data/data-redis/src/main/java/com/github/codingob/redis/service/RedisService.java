package com.github.codingob.redis.service;

import com.github.codingob.redis.model.RedisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author codingob
 */
@Service
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void transactionService(){
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <String, V> Object execute(RedisOperations<String, V> redisOperations) throws DataAccessException {
                redisOperations.multi();
                redisOperations.opsForValue().set("redisTransaction",new RedisData());
                return redisOperations.exec();
            }
        });
    }
}
