package com.github.codingob.spring.amqp.service;

import com.github.codingob.spring.amqp.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@Service
@Slf4j
public class QueueService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void send(Object message) {
        rabbitTemplate.convertAndSend("queue", message);
        log.info("发送成功: " + message);
    }

    @RabbitListener(queues = "queue")
    @RabbitHandler
    public void receive(Entity entity) {
        log.info("消费者: " + entity);
    }
}
