# Spring Amqp

依赖

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.amqp</groupId>
        <artifactId>spring-rabbit</artifactId>
        <version>2.3.10</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.5</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.amqp</groupId>
        <artifactId>spring-rabbit-test</artifactId>
        <version>2.3.10</version>
    </dependency>
</dependencies>
```

## 配置

```properties
amqp.host=server
amqp.port=5672
amqp.virtualHost=/rabbitmq
amqp.username=guest
amqp.password=guest
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/rabbit
       http://www.springframework.org/schema/rabbit/spring-rabbit.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="amqp.properties"/>

    <rabbit:connection-factory id="connectionFactory" host="${amqp.host}" port="${amqp.port}"
                               username="${amqp.username}" password="${amqp.password}"
                               virtual-host="${amqp.virtualHost}"/>

    <rabbit:template id="amqpTemplate" connection-factory="connectionFactory"/>

    <rabbit:admin connection-factory="connectionFactory"/>

    <bean id="rabbitListenerContainerFactory"
          class="org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory">
        <property name="connectionFactory" ref="connectionFactory"/>
        <property name="prefetchCount" value="1"/>
    </bean>

    <context:annotation-config/>

</beans>
```

交换器

```java
@Configuration
public class ExchangeConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("demo.topic", true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("demo.fanout", true, false);
    }

    @Bean
    public DirectExchange routeExchange() {
        return new DirectExchange("demo.direct", true, false);
    }
}
```

队列

```java
@Configuration
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
```

绑定

```java
@Configuration
public class BindingConfig {

    @Bean
    public Binding directBinding1(Queue directQueueA, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueA).to(directExchange).with("0");
    }

    @Bean
    public Binding directBinding2(Queue directQueueB, DirectExchange directExchange) {
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
}
```

## 简单队列

```java
@Service
public class QueueConsumer {
    @RabbitListener(queues = {"queue"})
    @RabbitHandler
    public void receive(String message) {
        System.out.println("queue: " + message);
    }
}
```

## 工作队列

```java
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
```

## 发布订阅

```java
@Service
public class FanoutConsumer {
    @RabbitListener(queues = {"fanout.queue.A"})
    @RabbitHandler
    public void receiveA(String message) {
        System.out.println("fanout.queue.A: " + message);
    }

    @RabbitListener(queues = {"fanout.queue.B"})
    @RabbitHandler
    public void receiveB(String message) {
        System.out.println("fanout.queue.B: " + message);
    }
}
```

## 路由模式

```java
@Service
public class DirectConsumer {
    @RabbitListener(queues = {"direct.queue.A"})
    @RabbitHandler
    public void receive1(String message) {
        System.out.println("direct.queue.1: " + message);
    }

    @RabbitListener(queues = {"direct.queue.B"})
    @RabbitHandler
    public void receive2(String message) {
        System.out.println("direct.queue.2: " + message);
    }
}
```

## 主题模式

```java
@Service
public class TopicConsumer {
    @RabbitListener(queues = {"topic.queue.A"})
    @RabbitHandler
    public void receiveA(String message) {
        System.out.println("topic.queue.A: " + message);
    }

    @RabbitListener(queues = {"topic.queue.B"})
    @RabbitHandler
    public void receiveB(String message) {
        System.out.println("topic.queue.B: " + message);
    }
}
```

## RPC模式

```java
// TODO
```
