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
public class DataSource {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public void close(){

    }
}
