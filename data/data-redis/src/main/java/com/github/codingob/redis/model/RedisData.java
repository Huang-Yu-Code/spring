package com.github.codingob.redis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Redis数据
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class RedisData implements Serializable {
    private Long id;
    private String name;
    private boolean gender;
    private BigDecimal money;
    private Timestamp createTime;
    private boolean delete;

    public RedisData(Long id, String name, boolean gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public RedisData(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
