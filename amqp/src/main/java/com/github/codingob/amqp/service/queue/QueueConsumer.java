package com.github.codingob.amqp.service.queue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 简单队列(P2P)
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class QueueConsumer {
    @RabbitListener(queues = {"queue"})
    @RabbitHandler
    public void receive(String message) {
        System.out.println("queue: " + message);
    }
}
