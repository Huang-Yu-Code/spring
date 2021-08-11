package com.demo.spring.bean.annotation.config;

import com.demo.spring.bean.annotation.Bean5;
import com.demo.spring.bean.xml.BeanInstanceFactory;
import com.demo.spring.bean.xml.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * @author yu
 */
@Profile("default")
@PropertySource("classpath:jdbc.properties")
@ImportResource("classpath:bean2.xml")
@Import(Bean5.class)
public class BeanConfig {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public com.demo.spring.bean.xml.Bean bean1() {
        com.demo.spring.bean.xml.Bean bean = new com.demo.spring.bean.xml.Bean();
        bean.setId(1);
        bean.setName("annotation-bean-1");
        return bean;
    }

    @Bean
    public com.demo.spring.bean.xml.Bean bean3() {
        return new com.demo.spring.bean.xml.Bean(3, "annotation-bean-3");
    }

    @Bean
    public BeanInstanceFactory beanInstanceFactory(){
        return new BeanInstanceFactory();
    }

    @Bean
    public com.demo.spring.bean.xml.Bean bean4(BeanInstanceFactory beanInstanceFactory) {
        return beanInstanceFactory.createBean(4, "annotation-bean-4");
    }

    @Bean
    public DataSource dataSource(@Value("${jdbc.driverClassName:com.mysql.cj.jdbc.Driver}")String driverClassName,
                                 @Value("${jdbc.url:jdbc:mysql://localhost:3306/database}")String url,
                                 @Value("${jdbc.username:root}")String username,
                                 @Value("${jdbc.password:root}")String password) {
        return new DataSource(driverClassName, url, username, password);
    }

}
