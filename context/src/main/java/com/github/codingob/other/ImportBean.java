package com.github.codingob.other;

import com.github.codingob.context.bean.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
public class ImportBean {
    @Bean
    public DataSource dataSource3(){
        return new DataSource();
    }
}
