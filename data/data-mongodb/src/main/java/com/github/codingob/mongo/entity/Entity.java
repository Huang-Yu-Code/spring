package com.github.codingob.mongo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** 实体类
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class Entity implements Serializable {
    private Long id;
    private String name;
    private boolean gender;
    private BigDecimal money;
    private Timestamp createTime;
    private boolean isDelete;

    public Entity(Long id, String name, boolean gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}
