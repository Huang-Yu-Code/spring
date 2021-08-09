package com.github.codingob.webmvc.dao;

import com.github.codingob.webmvc.model.User;
import org.springframework.stereotype.Repository;

/**
 * UserDao
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Repository
public class UserDao {

    public User getUser() {
        return new User("root", "root");
    }
}
