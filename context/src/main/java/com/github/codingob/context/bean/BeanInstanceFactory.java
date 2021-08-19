package com.github.codingob.context.bean;

/**
 * 实例Bean工厂
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class BeanInstanceFactory {
    public DemoBean createBean(int id, String name){
        return new DemoBean(id, name);
    }
}
