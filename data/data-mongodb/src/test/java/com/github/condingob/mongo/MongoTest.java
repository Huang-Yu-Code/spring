package com.github.condingob.mongo;

import com.github.codingob.mongo.config.MongoConfig;
import com.github.codingob.mongo.entity.Entity;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

/**
 * MongoDB测试类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Slf4j
@SpringJUnitConfig(MongoConfig.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void insert() {
        Entity entity = new Entity(1L, "mongodb", false);
        Entity insert = mongoTemplate.insert(entity);
        log.info(insert.toString());
    }

    @Test
    void query() {
        Entity entity = mongoTemplate.findById(1L, Entity.class);
        Assertions.assertNotNull(entity);
        log.info(entity.getName());
    }

    @Test
    void update() {
        Criteria criteria = Criteria.where("id").is(1L);
        Query query = Query.query(criteria);
        Update update = Update.update("money", new BigDecimal(1000));
        UpdateResult result = mongoTemplate.updateFirst(query, update, Entity.class);
        log.info(result.toString());
    }

    @Test
    void delete() {
        Entity entity = mongoTemplate.findById(1, Entity.class);
        Assertions.assertNotNull(entity);
        DeleteResult result = mongoTemplate.remove(entity);
        log.info(String.valueOf(result.getDeletedCount()));
    }
}
