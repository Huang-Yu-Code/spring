package com.demo.spring.mybatis.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author codingob
 */
@PropertySource("classpath:mybatis.properties")
@MapperScan("com.demo.spring.mybatis.mapper")
@ComponentScan("com.demo.spring.mybatis")
@EnableTransactionManagement
public class MybatisConfig {
    @Value("${mybatis.driverClassName}")
    private String driverClassName;
    @Value("${mybatis.url}")
    private String url;
    @Value("${mybatis.username}")
    private String username;
    @Value("${mybatis.password}")
    private String password;
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Value("${mybatis.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;

    @Bean
    public DataSource dataSource() {
        return new PooledDataSource(driverClassName, url, username, password);
    }

    /**
     * 加载Mapper.xml
     * @return Resource
     * @throws IOException IO异常
     */
    @Bean
    public Resource[] resources() throws IOException {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources(mapperLocations);
    }

    /**
     * 加载Mybatis配置信息
     * @return Configuration
     */
    @Bean
    public org.apache.ibatis.session.Configuration configuration(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        return configuration;
    }

    /**
     * 分页插件
     * @return Interceptor
     */
    @Bean
    public Interceptor pagePlugin(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("params","mysql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    /**
     * 插件配置
     * @param interceptors 插件
     * @return Interceptor[]
     */
    @Bean
    public Interceptor[] interceptors(Interceptor...interceptors){
        return interceptors;
    }

    /**
     * 创建工厂SqlSessionFactory
     * @return SqlSessionFactory
     * @throws Exception Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource,
                                               Resource[] resources,
                                               Configuration configuration,
                                               Interceptor[] interceptors
                                               ) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(resources);
        factoryBean.setConfiguration(configuration);
        factoryBean.setPlugins(interceptors);
        return factoryBean.getObject();
    }

    /**
     * 交由Spring容器管理事务
     * @return transactionManager
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
