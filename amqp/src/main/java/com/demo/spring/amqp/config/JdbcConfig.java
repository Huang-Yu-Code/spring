package com.demo.spring.amqp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {
}
