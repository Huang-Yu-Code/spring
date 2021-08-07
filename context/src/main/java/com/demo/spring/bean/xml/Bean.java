package com.demo.spring.bean.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codingob
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bean {
    private int id;
    private String name;

    public void init() {
        System.out.println("Bean 初始化方法...");
    }

    public void destroy() {
        System.out.println("Bean 销毁方法...");
    }
}
