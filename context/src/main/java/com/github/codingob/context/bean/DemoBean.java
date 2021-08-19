package com.github.codingob.context.bean;

import java.util.Objects;

/**
 * Bean
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class DemoBean {
    private int id;
    private String name;

    public void init() {
        System.out.println("Bean 初始化方法...");
    }

    public void destroy() {
        System.out.println("Bean 销毁方法...");
    }

    public DemoBean() {
    }

    public DemoBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DemoBean bean = (DemoBean) o;
        return id == bean.id && Objects.equals(name, bean.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
