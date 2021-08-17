package com.github.codingob.web.mvc.controller;

import com.github.codingob.web.mvc.dto.Logon;
import com.github.codingob.web.mvc.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitWebConfig(locations = "classpath:context.xml")
public class MvcControllerTest {
    @Autowired
    private MvcController mvcController;

    @Test
    void logon() {
        Logon logon = new Logon();
        logon.setUsername("12345");
        logon.setPassword("12345");
        logon.setRePassword("12345");
        Response response = mvcController.logon(logon);
        System.out.println(response);
    }

    @Test
    void download(){

    }
}
