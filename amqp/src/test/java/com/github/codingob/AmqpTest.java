package com.github.codingob;

import com.github.codingob.amqp.config.AmqpConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 测试类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitConfig(AmqpConfig.class)
@SpringRabbitTest
public class AmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendQueue() {
        rabbitTemplate.convertAndSend("queue", "Hello World!");
    }

    @Test
    void sendWorkLoopQueue() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work.queue.loop", "Hello World!");
        }
    }

    @Test
    void sendWorkFairQueue() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work.queue.fair", "Hello World!");
        }
    }

    @Test
    void sendDirectExchange() {
        rabbitTemplate.convertAndSend("demo.direct", "0", "routingKey:0");
        rabbitTemplate.convertAndSend("demo.direct", "1", "routingKey:1");
    }

    @Test
    void sendFanoutExchange() {
        rabbitTemplate.convertAndSend("demo.fanout", "", "fanout");
    }

    @Test
    void sendTopicExchange() {
        rabbitTemplate.convertAndSend("demo.topic", "mysql", "mysql");
        rabbitTemplate.convertAndSend("demo.topic", "mysql.topic", "mysql.topic");
        rabbitTemplate.convertAndSend("demo.topic", "mysql.topic.ok", "mysql.topic.ok");
    }

    @Test
    void sendACK(){
        rabbitTemplate.convertAndSend("","ack.queue","ack.queue",new CorrelationData("1"));
    }

    @Test
    void sendTTL(){
        rabbitTemplate.convertAndSend("ttl.queue","ttl.queue");

        rabbitTemplate.convertAndSend("", "queue", "queue", message -> {
            message.getMessageProperties().setExpiration("2000");
            message.getMessageProperties().setContentEncoding("UTF-8");
            return message;
        });
    }

}
