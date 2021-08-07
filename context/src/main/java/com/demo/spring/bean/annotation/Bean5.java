package com.demo.spring.bean.annotation;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;

/**
 * @author codingob
 */
@Configuration
public class Bean5 {
    @Bean
    @Scope("prototype")
    @Description("Provides a basic example of a bean")
    public com.demo.spring.bean.xml.Bean bean5(){
        return new com.demo.spring.bean.xml.Bean(5,"annotation-bean-5");
    }
}
