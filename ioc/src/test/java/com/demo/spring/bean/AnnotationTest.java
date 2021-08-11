package com.demo.spring.bean;

import com.demo.spring.bean.annotation.Application;
import com.demo.spring.bean.xml.Bean;
import com.demo.spring.bean.xml.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(Application.class)
public class AnnotationTest {
    @Autowired
    private Bean bean1;

    @Autowired
    private Bean bean2;

    @Autowired
    private Bean bean3;

    @Autowired
    private Bean bean4;

    @Autowired
    private Bean bean5;

    @Autowired
    private DataSource dataSource;

    @Test
    void propertySource() {
        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);
        System.out.println(bean4);
        System.out.println(bean5);
        System.out.println(dataSource);
    }
}
