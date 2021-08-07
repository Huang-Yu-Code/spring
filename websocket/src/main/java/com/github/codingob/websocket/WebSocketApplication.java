package com.github.codingob.websocket;

import com.github.codingob.websocket.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class WebSocketApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
