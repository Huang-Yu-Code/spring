package com.demo.spring.amqp.servvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
@Slf4j
public class ProviderService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void sendQueue(Object message) {
        rabbitTemplate.convertAndSend("queue", message);
        log.info("发送成功: " + message);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendWork(Object message) {
        rabbitTemplate.convertAndSend("work", message);
        log.info("发送成功: " + message);
    }
}
