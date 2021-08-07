package com.github.codingob.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * WebMvc配置类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.github.codingob.websocket")
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        return new InternalResourceViewResolver("/jsp/", ".jsp");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
