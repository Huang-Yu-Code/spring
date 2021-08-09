package com.github.codingob.webmvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
