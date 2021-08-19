package com.github.codingob.context.config;

import com.github.codingob.context.bean.BeanInstanceFactory;
import com.github.codingob.context.bean.DataSource;
import com.github.codingob.context.bean.DemoBean;
import com.github.codingob.other.ImportBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * Bean配置类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
@ImportResource(locations = "classpath:bean3.xml")
@Import(ImportBean.class)
@PropertySource("classpath:jdbc.properties")
@ComponentScan("com.github.codingob.context")
public class BeanConfig {
    @Bean
    public BeanInstanceFactory beanInstanceFactory2() {
        return new BeanInstanceFactory();
    }

    @Bean
    public DemoBean bean6() {
        return new DemoBean(6, "bean6");
    }

    @Bean
    @Scope("prototype")
    @DependsOn("bean1")
    public DemoBean bean7() {
        return beanInstanceFactory2().createBean(7, "bean7");
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public DataSource dataSource2(
            @Value("${jdbc.driverClassName}") String driverClassName,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }
}
