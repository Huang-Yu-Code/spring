package com.demo.spring.amqp.servvice;

import com.demo.spring.amqp.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
@Slf4j
public class ConsumerService {

    @RabbitListener(queues = "queue")
    @RabbitHandler
    public void receiveQueue(Entity entity) {
        log.info("简单队列: " + entity);
    }

    @RabbitListener(queues = "work")
    @RabbitHandler
    public void receiveWork1(Entity entity) {
        log.info("工作队列1: " + entity);
    }

    @RabbitListener(queues = "work")
    @RabbitHandler
    public void receiveWork2(Entity entity) {
        log.info("工作队列2: " + entity);
    }
}
