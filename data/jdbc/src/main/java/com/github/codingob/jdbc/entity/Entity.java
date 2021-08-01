package com.github.codingob.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("entity")
public class Entity implements Serializable {
    @Id
    private Long id;
    private String name;
    private boolean gender;
    private BigDecimal money;
    @Column("create_time")
    private Timestamp createTime;
    @Column("is_delete")
    private boolean isDelete;

    @PersistenceConstructor
    public Entity(Long id, String name, boolean gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    Entity withId(Long id) {
        return new Entity(id, this.name, this.gender,this.money,this.createTime,this.isDelete);
    }
}
