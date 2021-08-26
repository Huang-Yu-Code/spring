package com.github.codingob.amqp.service.queue;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
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
    public void receive(String msg, Channel channel, Message message) {
        System.out.println("queue: " + msg);
        System.out.println(channel);
        System.out.println(message);
    }
}
