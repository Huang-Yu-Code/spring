package com.github.codingob.web.mvc.service;

import com.github.codingob.web.mvc.dto.Login;
import com.github.codingob.web.mvc.dto.Logon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitWebConfig(locations = "classpath:context.xml")
public class MvcServiceTest {
    @Autowired
    private MvcService mvcService;

    @Test
    void logon(){
        mvcService.getCode(System.currentTimeMillis());
        Logon logon = new Logon();
        logon.setUsername("12345");
        logon.setPassword("12345");
        logon.setRePassword("12345");
        logon.setCode((String) mvcService.getHttpSession().getAttribute("code"));
        mvcService.logon(logon);
    }

    @Test
    void login(){
        mvcService.getCode(System.currentTimeMillis());
        Login login = new Login();
        login.setUsername("12345");
        login.setPassword("12345");
        login.setCode((String) mvcService.getHttpSession().getAttribute("code"));
        mvcService.login(login);
    }

    @Test
    void logout(){

    }
}
