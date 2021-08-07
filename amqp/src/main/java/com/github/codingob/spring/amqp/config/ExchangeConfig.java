package com.github.codingob.spring.amqp.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@Configuration
public class ExchangeConfig {
    @Bean
    public Exchange topicExchange() {
        return new TopicExchange("demo.topic");
    }

    @Bean
    public Exchange publishExchange() {
        return new FanoutExchange("demo.fanout");
    }

    @Bean
    public Exchange routeExchange() {
        return new DirectExchange("demo.direct");
    }
}
