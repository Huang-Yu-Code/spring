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
    @RabbitListener(queues = {"queue"}, ackMode = "MANUAL")
    @RabbitHandler
    public void receive(String msg, Channel channel, Message message) throws IOException {
        System.out.println("queue: " + msg);
        System.out.println(channel);
        System.out.println(message);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            // 拒绝确认消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            // 拒绝消息
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
