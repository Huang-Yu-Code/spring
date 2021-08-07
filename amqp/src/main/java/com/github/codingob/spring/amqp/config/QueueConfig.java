package com.github.codingob.spring.amqp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@Configuration
public class QueueConfig {
    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    @Bean
    public Queue workFair() {
        return new Queue("work-fair");
    }

    @Bean
    public Queue workLoop() {
        return new Queue("work-loop");
    }
}
