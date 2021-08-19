package com.github.codingob.amqp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@Configuration
@EnableRabbit
public class QueueConfig {
    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    @Bean
    public Queue workFairQueue() {
        return new Queue("work.queue.fair");
    }

    @Bean
    public Queue workLoopQueue() {
        return new Queue("work.queue.loop");
    }

    @Bean
    public Queue fanoutQueueA(){
        return new Queue("fanout.queue.A");
    }

    @Bean
    public Queue fanoutQueueB(){
        return new Queue("fanout.queue.B");
    }

    @Bean
    public Queue directQueueA(){
        return new Queue("direct.queue.A");
    }

    @Bean
    public Queue directQueueB(){
        return new Queue("direct.queue.B");
    }

    @Bean
    public Queue topicQueueA(){
        return new Queue("topic.queue.A");
    }

    @Bean
    public Queue topicQueueB(){
        return new Queue("topic.queue.B");
    }

}
