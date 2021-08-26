package com.github.codingob.amqp.service.dead;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 死信队列
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class DeadConsumer {
    @RabbitListener(queues = {"dead.queue"})
    @RabbitHandler
    public void receive(String message) {
        System.out.println("dead.queue: " + message);
    }
}
