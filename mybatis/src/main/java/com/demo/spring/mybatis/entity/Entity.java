package com.demo.spring.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author codingob
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entity implements Serializable {
    private Integer id;
    private String name;
    private Boolean gender;
    private BigDecimal money;
    private Timestamp createTime;
}
