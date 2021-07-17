package com.demo.spring.mvc.model;

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
