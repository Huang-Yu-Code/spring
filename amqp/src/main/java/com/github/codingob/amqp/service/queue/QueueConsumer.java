package com.github.codingob.amqp.service.queue;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 简单队列(P2P)
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class QueueConsumer {
    private static int NUM = 0;

    @RabbitListener(queues = {"queue"}, ackMode = "MANUAL")
    @RabbitHandler
    public void receive(String msg, Channel channel, Message message) throws IOException {
        try {
            int i = 1 / 0;
            System.out.println("queue: " + msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (NUM < 3) {
                // 拒绝确认消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                System.out.println("消息已拒绝确认,重回队列---" + NUM);
                NUM++;
            } else {
                // 拒绝消息
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("消息已拒绝,从队列中删除");
                NUM=0;
            }
        }
    }
}
