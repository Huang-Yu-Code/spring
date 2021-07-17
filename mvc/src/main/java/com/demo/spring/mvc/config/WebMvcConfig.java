package com.demo.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;

/**
 * @author codingob
 */
@EnableWebMvc
@ComponentScan(value = "com.demo.spring.mvc",
        excludeFilters={@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = WebMvcConfig.class)})
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
