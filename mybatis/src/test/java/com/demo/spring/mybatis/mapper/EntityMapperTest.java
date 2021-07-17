package com.demo.spring.mybatis.mapper;

import com.demo.spring.mybatis.config.MybatisConfig;
import com.demo.spring.mybatis.entity.Entity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * @author codingob
 */
@SpringJUnitConfig(MybatisConfig.class)
@Slf4j
public class EntityMapperTest {
    private final static int ID = 1;
    private final static int PAGE = 1;
    private final static int SIZE = 10;

    @Autowired
    private EntityMapper entityMapper;

    @Test
    void create() {
        Entity entity = new Entity();
        entity.setId(ID);
        entity.setName("insert");
        log.info(entity.toString());
        int i = entityMapper.create(entity);
        Assertions.assertEquals(1, i);
    }

    @Test
    void delete() {
        int i = entityMapper.deleteById(1);
        Assertions.assertEquals(1, i);
    }

    @Test
    void update() {
        Entity entity = entityMapper.getById(ID);
        log.info("修改前: " + entity);
        entity.setName("update");
        int i = entityMapper.update(entity);
        log.info("修改后: " + entityMapper.getById(ID));
        Assertions.assertEquals(1, i);
    }

    @Test
    void getById() {
        Entity entity = entityMapper.getById(ID);
        log.info(entity.toString());
        Assertions.assertNotNull(entity);
    }

    @Test
    void getListByPage() {
        PageHelper.startPage(PAGE, SIZE);
        List<Entity> entityList = entityMapper.getListByPage();
        PageInfo<Entity> entityPageInfo = new PageInfo<>(entityList, SIZE);
        Assertions.assertEquals(1, entityPageInfo.getSize());
    }
}
