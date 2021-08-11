package com.github.coding.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
@Import({ShiroBeanConfiguration.class, ShiroConfiguration.class, ShiroAnnotationProcessorConfiguration.class})
public class ShiroConfig {
    private SecurityManager securityManager;

    @Autowired
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @PostConstruct
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }

}
