package com.github.codingob.spring.amqp.service;

import com.github.codingob.spring.amqp.entity.Entity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@Service
@Slf4j
public class WorkFairService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void send(Object message) {
        rabbitTemplate.convertAndSend("work-fair", message);
        log.info("发送成功: " + message);
    }

    @RabbitListener(queues = "work-fair", ackMode = "MANUAL")
    @RabbitHandler
    public void receive(Entity entity, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("工作队列1(公平): " + entity);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "work-fair")
    @RabbitHandler
    public void receive2(Entity entity) {
        log.info("工作队列2(公平): " + entity);
    }
}
