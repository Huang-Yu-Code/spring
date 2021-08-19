package com.github.codingob.shiro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
public class Logon {
    private String username;
    private String password;
    private String rePassword;
    private String code;
}
