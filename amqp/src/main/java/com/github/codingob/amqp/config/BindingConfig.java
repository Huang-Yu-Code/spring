package com.github.codingob.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 绑定配置
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
public class BindingConfig {

    @Bean
    public Binding directBinding1(Queue directQueueA, @Qualifier("routeExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueA).to(directExchange).with("0");
    }

    @Bean
    public Binding directBinding2(Queue directQueueB, @Qualifier("routeExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueB).to(directExchange).with("1");
    }

    @Bean
    public Binding fanoutBindingA(Queue fanoutQueueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBindingB(Queue fanoutQueueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
    }

    @Bean
    public Binding topicBindingA(Queue topicQueueA, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with("mysql.*");
    }

    @Bean
    public Binding topicBindingB(Queue topicQueueB, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("mysql.#");
    }

    @Bean
    public Binding deadBinding(Queue deadQueue,DirectExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("");
    }
}
