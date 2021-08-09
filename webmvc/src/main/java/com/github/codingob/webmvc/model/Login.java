package com.github.codingob.webmvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class Login {
    private User user;
    private String code;
}
