package com.github.codingob;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 测试类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitConfig
@SpringRabbitTest
public class AmqpTest {
    @Configuration
    @PropertySource("classpath:amqp.properties")
    @EnableRabbit
    public static class Config {
        @Value("${amqp.host}")
        private String host;
        @Value("${amqp.port}")
        private int port;
        @Value("${amqp.virtualHost}")
        private String virtualHost;
        @Value("${amqp.username}")
        private String username;
        @Value("${amqp.password}")
        private String password;

        @Bean
        public CachingConnectionFactory connectionFactory() {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setPort(port);
            connectionFactory.setVirtualHost(virtualHost);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            return connectionFactory;
        }

        @Bean
        public RabbitAdmin amqpAdmin() {
            return new RabbitAdmin(connectionFactory());
        }

        @Bean
        public RabbitTemplate rabbitTemplate() {
            return new RabbitTemplate(connectionFactory());
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendQueue() {
        rabbitTemplate.convertAndSend("queue", "Hello World!");
    }

    @Test
    void sendWorkLoopQueue() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work.queue.loop", "Hello World!");
        }
    }

    @Test
    void sendWorkFairQueue() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work.queue.fair", "Hello World!");
        }
    }

    @Test
    void sendDirectExchange() {
        rabbitTemplate.convertAndSend("demo.direct", "0", "routingKey:0");
        rabbitTemplate.convertAndSend("demo.direct", "1", "routingKey:1");
    }

    @Test
    void sendFanoutExchange() {
        rabbitTemplate.convertAndSend("demo.fanout", "", "fanout");
    }

    @Test
    void sendTopicExchange() {
        rabbitTemplate.convertAndSend("demo.topic", "mysql", "mysql");
        rabbitTemplate.convertAndSend("demo.topic", "mysql.topic", "mysql.topic");
        rabbitTemplate.convertAndSend("demo.topic", "mysql.topic.ok", "mysql.topic.ok");
    }

}
