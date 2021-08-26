package com.github.codingob.amqp.config;

import org.springframework.amqp.core.DirectExchange;
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
    public TopicExchange topicExchange() {
        return new TopicExchange("demo.topic", true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("demo.fanout", true, false);
    }

    @Bean
    public DirectExchange routeExchange() {
        return new DirectExchange("demo.direct", true, false);
    }

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("demo.dead", true, false);
    }
}
