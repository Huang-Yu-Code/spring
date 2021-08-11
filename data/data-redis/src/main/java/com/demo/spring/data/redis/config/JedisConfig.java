package com.demo.spring.data.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
@Import(BeanConfig.class)
public class JedisConfig {
    @Bean
    public JedisConnectionFactory redisConnectionFactory(@Value("${redis.host}") String host, @Value("${redis.port}") int port) {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }
}
