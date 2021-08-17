package com.github.codingob.data.jdbc.dao;

import com.github.codingob.data.jdbc.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Dao
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Repository
public class EntityDao {
    private String sql;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(Entity entity) {
        sql = "INSERT INTO Entity(NAME,GENDER,MONEY,CREATE_TIME,DELETE) VALUES(?,?,?,?,?)";
        return jdbcTemplate.update(sql, entity.getName(), entity.isGender(), new BigDecimal(1000), new Timestamp(System.currentTimeMillis()), entity.isDelete());
    }

    public Entity getById(int id) {
        sql = "SELECT * FROM ENTITY WHERE ID=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Entity.class), id);
    }

    public List<Entity> getByList(int page, int size) {

        sql = "SELECT * FROM ENTITY LIMIT ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Entity.class), (page - 1) * size, size);
    }

    public int updateById(Entity entity) {
        sql = "UPDATE ENTITY SET NAME=?,GENDER=?,MONEY=? WHERE ID=?";
        return jdbcTemplate.update(sql, entity.getName(), entity.isGender(), entity.getMoney(), entity.getId());
    }

    public int deleteById(int id) {
        sql = "DELETE FROM ENTITY WHERE ID=?";
        return jdbcTemplate.update(sql, id);
    }
}
