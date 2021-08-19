package com.github.codingob.amqp.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * Amqp配置
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@PropertySource("classpath:amqp.properties")
public class AmqpConfig {
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
        connectionFactory.setPublisherReturns(true);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("消息路由失败!");
            System.out.println(returnedMessage);
        });
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if (b) {
                System.out.println("消息投递成功");
            } else {
                System.out.println("消息投递失败 " + s);
            }
        });
        return rabbitTemplate;
    }
}
