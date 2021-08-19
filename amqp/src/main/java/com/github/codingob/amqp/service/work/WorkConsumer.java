package com.github.codingob.amqp.service.work;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 工作队列
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class WorkConsumer {
    @RabbitListener(queues = {"work.queue.fair"})
    @RabbitHandler
    public void receiveFair1(String message) {
        System.out.println("work.queue.fair1: " + message);
    }

    @RabbitListener(queues = {"work.queue.fair"})
    @RabbitHandler
    public void receiveFair2(String message) {
        service();
        System.out.println("work.queue.fair2: " + message);
    }

    @RabbitListener(queues = {"work.queue.loop"})
    @RabbitHandler
    public void receiveLoop1(String message) {
        service();
        System.out.println("work.queue.loop1: " + message);
    }

    @RabbitListener(queues = {"work.queue.loop"})
    @RabbitHandler
    public void receiveLoop2(String message) {
        service();
        System.out.println("work.queue.loop2: " + message);
    }

    private void service(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
