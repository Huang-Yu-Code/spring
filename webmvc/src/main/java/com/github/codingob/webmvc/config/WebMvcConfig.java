package com.github.codingob.webmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;

/**
 * WebMvc配置类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private HandlerInterceptor authInterceptor;

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        return new InternalResourceViewResolver("/jsp/", ".jsp");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(authInterceptor);
        interceptorRegistration.addPathPatterns("/home/**");
    }
}
