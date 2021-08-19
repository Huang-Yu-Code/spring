package com.github.codingob.context.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DataSource
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSource {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public void init() {
        System.out.println("Bean 初始化方法...");
    }

    public void destroy() {
        System.out.println("Bean 销毁方法...");
    }
}
