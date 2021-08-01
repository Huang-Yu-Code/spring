package com.github.codingob.jdbc;

import com.github.codingob.jdbc.config.JdbcConfig;
import com.github.codingob.jdbc.entity.Entity;
import com.github.codingob.jdbc.repository.EntityRepository;
import com.github.codingob.jdbc.service.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Slf4j
@SpringJUnitConfig(JdbcConfig.class)
public class JdbcTest {
    @Autowired
    private EntityRepository entityRepository;

    @Test
    void create() {
        Entity entity = new Entity(1L, "jdbc", false);
        entityRepository.save(entity);
    }
}
