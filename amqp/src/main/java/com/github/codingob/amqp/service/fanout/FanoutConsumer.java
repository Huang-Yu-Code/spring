package com.github.codingob.amqp.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 广播模式
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class FanoutConsumer {
    @RabbitListener(queues = {"fanout.queue.A"})
    @RabbitHandler
    public void receiveA(String message) {
        System.out.println("fanout.queue.A: " + message);
    }

    @RabbitListener(queues = {"fanout.queue.B"})
    @RabbitHandler
    public void receiveB(String message) {
        System.out.println("fanout.queue.B: " + message);
    }
}
