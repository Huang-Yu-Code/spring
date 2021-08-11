package com.github.codingob.data.jdbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 实体类
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class Entity {
    private Integer id;
    private String name;
    private boolean gender;
    private BigDecimal money;
    private Timestamp createTime;
    private boolean isDelete;

    public Entity(String name, boolean gender) {
        this.name = name;
        this.gender = gender;
    }
}
