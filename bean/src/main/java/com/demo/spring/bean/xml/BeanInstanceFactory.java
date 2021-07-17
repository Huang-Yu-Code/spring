package com.demo.spring.bean.xml;

/**
 * @author codingob
 */
public class BeanInstanceFactory {
    public Bean createBean(int id, String name){
        return new Bean(id, name);
    }
}
