package com.github.codingob.webmvc.config;

import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
@Import({ShiroBeanConfiguration.class,})
public class ShiroConfig {
}
