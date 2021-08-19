package com.github.codingob.amqp.service.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 主题模式
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class TopicConsumer {
    @RabbitListener(queues = {"topic.queue.A"})
    @RabbitHandler
    public void receiveA(String message) {
        System.out.println("topic.queue.A: " + message);
    }

    @RabbitListener(queues = {"topic.queue.B"})
    @RabbitHandler
    public void receiveB(String message) {
        System.out.println("topic.queue.B: " + message);
    }
}
