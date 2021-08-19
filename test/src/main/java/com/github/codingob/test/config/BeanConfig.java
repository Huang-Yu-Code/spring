package com.github.codingob.test.config;

import com.github.codingob.test.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
public class BeanConfig {
    @Bean
    public Account account(){
        Account account = new Account();
        account.setId(1L);
        account.setUsername("admin");
        account.setPassword("admin");
        return account;
    }
}
