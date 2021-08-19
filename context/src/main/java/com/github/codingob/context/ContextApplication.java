package com.github.codingob.context;

import com.github.codingob.context.bean.DataSource;
import com.github.codingob.context.bean.DemoBean;
import com.github.codingob.context.component.DemoComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Application
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class ContextApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        DemoBean bean1 = context.getBean("bean1", DemoBean.class);
        System.out.println(bean1.getId());
        System.out.println(bean1.getName());
        DemoBean bean4 = context.getBean("bean4", DemoBean.class);
        System.out.println(bean4.getId());
        System.out.println(bean4.getName());

        DemoComponent demoComponent = context.getBean("demoComponent", DemoComponent.class);
        demoComponent.showBean1();
        demoComponent.showBean4();
        System.out.println(bean1==demoComponent.getBean1());
        System.out.println(bean4==demoComponent.getBean4());

        DemoComponent demoComponent1 = context.getBean("demoComponent1", DemoComponent.class);
        demoComponent1.showBean1();
        demoComponent1.showBean4();
        System.out.println(bean1==demoComponent1.getBean1());
        System.out.println(bean1==demoComponent1.getBean4());

        DataSource dataSource3 = context.getBean("dataSource3", DataSource.class);
        System.out.println(dataSource3);
    }
}
