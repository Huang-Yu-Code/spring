package com.github.codingob.test.dao;

import org.springframework.stereotype.Repository;

/**
 * Dao
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Repository
public class WebDao {
    public void create() {
        System.out.println("create");
    }

    public void read() {
        System.out.println("read");
    }

    public void update() {
        System.out.println("update");
    }

    public void delete() {
        System.out.println("delete");
    }
}
