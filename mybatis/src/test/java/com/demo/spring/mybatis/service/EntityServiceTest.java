package com.demo.spring.mybatis.service;

import com.demo.spring.mybatis.config.MybatisConfig;
import com.demo.spring.mybatis.entity.Entity;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

/**
 * @author codingob
 */
@SpringJUnitConfig(MybatisConfig.class)
@Slf4j
public class EntityServiceTest {
    @Autowired
    private EntityService entityService;
    private final static int ID1 = 3;
    private final static int ID2 = 5;
    private final static BigDecimal MONEY = new BigDecimal(1000);
    private final static BigDecimal TRANSFER_MONEY = new BigDecimal(100);

    @Test
    void initial() {
        int i;
        i = entityService.initial(ID1, MONEY);
        Assertions.assertEquals(1, i);
        i = entityService.initial(ID2, MONEY);
        Assertions.assertEquals(1, i);
    }

    @Test
    void transfer() {
        BigDecimal money1 = entityService.getMoneyById(ID1);
        BigDecimal money2 = entityService.getMoneyById(ID2);
        log.info(ID1 + " 转账前: " + money1);
        log.info(ID2 + " 转账前: " + money2);
        try {
            entityService.transfer(ID1, ID2, TRANSFER_MONEY);
            money1 = entityService.getMoneyById(ID1);
            money2 = entityService.getMoneyById(ID2);
            Assertions.assertEquals(MONEY.subtract(TRANSFER_MONEY), money1);
            Assertions.assertEquals(MONEY.add(TRANSFER_MONEY), money2);
        } catch (Exception e) {
            Assertions.assertEquals(MONEY, money1);
            Assertions.assertEquals(MONEY, money2);
            e.printStackTrace();
        }
        log.info(ID1 + " 转账后: " + money1);
        log.info(ID2 + " 转账后: " + money2);
    }

    @Test
    void clear() {
        int i;
        i = entityService.clear(ID1);
        Assertions.assertEquals(1, i);
        i = entityService.clear(ID2);
        Assertions.assertEquals(1, i);
    }

    @Test
    void getListByPage(){
        for (int i = 1; i < 21; i++) {
            entityService.initial(i,new BigDecimal(1000));
        }
        PageInfo<Entity> listByPage = entityService.getListByPage(1, 10);
        log.info(listByPage.getList().toString());
        Assertions.assertEquals(20, listByPage.getTotal());
        for (int i = 1; i < 21; i++) {
            entityService.clear(i);
        }
    }
}
