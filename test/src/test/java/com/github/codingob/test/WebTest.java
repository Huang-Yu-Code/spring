package com.github.codingob.test;

import com.github.codingob.test.controller.WebController;
import com.github.codingob.test.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

/**
 * Web应用测试
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitWebConfig(locations = "classpath:mvc.xml")
public class WebTest {
    @Autowired
    private Account account;

    @Autowired
    private WebController controller;

    @Test
    void test() {
        controller.xxxCreate();
        controller.xxxRead();
        controller.xxxUpdate();
        controller.xxxDelete();
        System.out.println(account);
    }
}
