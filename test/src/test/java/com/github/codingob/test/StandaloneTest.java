package com.github.codingob.test;

import com.github.codingob.test.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 非Web应用测试
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitConfig(locations = "classpath:context.xml")
public class StandaloneTest {
    @Autowired
    private Account account;

    @Test
    void test() {
        System.out.println(account);
        ;
    }
}
