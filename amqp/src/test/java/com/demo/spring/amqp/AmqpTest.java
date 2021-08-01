package com.demo.spring.amqp;

import com.demo.spring.amqp.config.AmqpConfig;
import com.demo.spring.amqp.entity.Entity;
import com.demo.spring.amqp.servvice.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitConfig(AmqpConfig.class)
@SpringRabbitTest
public class AmqpTest {

    @Autowired
    private ProviderService providerService;

    @Test
    void sendQueue() {
        Entity entity = new Entity(1L, "rabbitmq", false);
        providerService.sendQueue(entity);
    }

    @Test
    void SendWork() {
        for (long i = 0L; i < 10; i++) {
            Entity entity = new Entity(i, "rabbitmq", false);
            providerService.sendWork(entity);
        }
    }
}
