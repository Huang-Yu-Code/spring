package com.github.codingob.context.component;

import com.github.codingob.context.bean.DemoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
public class DemoComponent {
    private DemoBean bean1;
    private DemoBean bean4;

    @Autowired
    public void setBean1(DemoBean bean1) {
        this.bean1 = bean1;
    }

    @Autowired
    public void setBean4(DemoBean bean4) {
        this.bean4 = bean4;
    }

    public void showBean1(){
        System.out.println(bean1.getId());
        System.out.println(bean1.getName());
    }

    public void showBean4(){
        System.out.println(bean4.getId());
        System.out.println(bean4.getName());
    }

    public DemoBean getBean1(){
        return bean1;
    }

    public DemoBean getBean4(){
        return bean4;
    }
}
