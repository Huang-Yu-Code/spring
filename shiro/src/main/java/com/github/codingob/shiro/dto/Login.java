package com.github.codingob.shiro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class Login {
    private String username;
    private String password;
    private String code;
}
