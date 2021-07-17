package com.demo.spring.bean.annotation;

import com.demo.spring.bean.annotation.config.BeanConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author codingob
 */
@ComponentScan("com.demo.spring.bean.annotation.config")
@Import(BeanConfig.class)
public class Application {
}
