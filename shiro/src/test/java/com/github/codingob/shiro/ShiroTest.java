package com.github.codingob.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.PostConstruct;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitConfig(locations = "classpath:context.xml")
public class ShiroTest {
    @Autowired
    private SecurityManager securityManager;

    @PostConstruct
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    void test(){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        System.out.println(principal);
        UsernamePasswordToken token = new UsernamePasswordToken("guest","guest");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        System.out.println(subject.getPrincipal());
        System.out.println(subject.hasRole("guest"));
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.isPermitted("guest:create"));
    }

}
