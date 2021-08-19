package com.github.codingob.amqp.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 路由模式
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class DirectConsumer {
    @RabbitListener(queues = {"direct.queue.A"})
    @RabbitHandler
    public void receive1(String message) {
        System.out.println("direct.queue.1: " + message);
    }

    @RabbitListener(queues = {"direct.queue.B"})
    @RabbitHandler
    public void receive2(String message) {
        System.out.println("direct.queue.2: " + message);
    }
}
