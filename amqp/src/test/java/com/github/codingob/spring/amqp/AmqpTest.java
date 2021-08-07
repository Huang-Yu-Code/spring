package com.github.codingob.spring.amqp;

import com.github.codingob.spring.amqp.config.AmqpConfig;
import com.github.codingob.spring.amqp.entity.Entity;
import com.github.codingob.spring.amqp.service.*;
import org.junit.jupiter.api.Test;
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
    private QueueService queueService;

    @Autowired
    private WorkFairService workFairService;

    @Autowired
    private WorkLoopService workLoopService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private PublishService publishService;

    @Test
    void queueService() {
        Entity entity = new Entity(1L, "rabbitmq");
        queueService.send(entity);
    }

    @Test
    void workFairService() {
        for (long i = 0L; i < 10; i++) {
            Entity entity = new Entity(i, "rabbitmq");
            workFairService.send(entity);
        }
    }

    @Test
    void workLoopService() {
        for (long i = 0L; i < 10; i++) {
            Entity entity = new Entity(i, "rabbitmq");
            workLoopService.send(entity);
        }
    }

    @Test
    void publishService() {
        for (long i = 0L; i < 10; i++) {
            Entity entity = new Entity(i, "rabbitmq");
            publishService.send(entity);
        }
    }

    @Test
    void routeService() {
        for (long i = 0L; i < 10; i++) {
            Entity entity = new Entity(i, "rabbitmq");
            routeService.send(entity, (int) i);
        }
    }

    @Test
    void topicService() {
        String[] routes = {
                "topic.orange.logs",
                "topic.orange.rabbit",
                "topic.logs.rabbit",
                "lazy.orange.logs",
                "lazy.orange.logs.rabbit"
        };
        long i = 0L;
        for (String route : routes) {
            Entity entity = new Entity(i, route);
            topicService.send(entity, route);
            i++;
        }
    }
}
