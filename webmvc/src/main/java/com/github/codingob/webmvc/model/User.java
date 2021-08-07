package com.github.codingob.webmvc.model;

import lombok.Data;

/**
 * @author codingob
 */
@Data
public class User {
    private Integer id;
    private String username = "root";
    private String password = "root";
}
