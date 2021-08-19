package com.github.codingob.amqp;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Application
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class AmqpApplication {
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:context.xml");
        context.registerShutdownHook();
    }
}
