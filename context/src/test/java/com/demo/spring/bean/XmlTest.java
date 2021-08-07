package com.demo.spring.bean;

import com.demo.spring.bean.xml.Bean;
import com.demo.spring.bean.xml.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:context.xml")
public class XmlTest {

    @Autowired
    private Bean bean1;

    @Autowired
    @Qualifier("bean2")
    private Bean bean2;

    @Autowired
    @Qualifier("bean3")
    private Bean bean3;

    @Autowired
    @Qualifier("bean4")
    private Bean bean4;

    @Autowired
    @Qualifier("bean5")
    private Bean bean5;

    @Autowired
    private DataSource dataSource;


    @Test
    void getBeans(){
        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);
        System.out.println(bean4);
        System.out.println(bean5);
        System.out.println(dataSource);
    }

}
