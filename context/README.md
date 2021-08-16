# Spring-IOC

# Bean

Bean定义

|属性|说明|
|:---:|:---:|
|class|类|
|id/name|名称|
|Constructor arguments|构造器注入|
|Properties|setter注入|
|Autowiring mode|自动装配|
|Lazy initialization mode|初始化方法|
|Destruction method|销毁方法|

Bean 命名约定

名称以小写字母开头，并从那里开始使用驼峰式大小写

别名

`<alias name="fromName" alias="toName"/>`

实例化

* 构造器
* 工厂方法
    * 静态工厂方法
    * 实例工厂方法

依赖注入

* 基于构造函数的依赖注入(强制依赖)
* 基于 Setter 的依赖注入(可选依赖)

循环依赖问题

* BeanCurrentlyInCreationException
* 避免构造函数注入并仅使用setter 注入

内部Bean

内部 bean 始终是匿名的，并且始终与外部 bean 一起创建

集合

`<list/>`、`<set/>`、`<map/>`、`<props/>`

List、Set、Map、Properties

自动装配

* no
* byName
* byType
* constructor

作用域

* singleton
* prototype
* prototype
* session
* application
* websocket

初始化方法
`init()`

销毁方法
`destroy()`

在非 Web 应用程序中优雅地关闭 Spring IoC 容器

```java
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Boot {

    public static void main(final String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

        // add a shutdown hook for the above context...
        ctx.registerShutdownHook();

        // app runs here...

        // main method exits, hook is called prior to the app shutting down...
    }
}
```

## 依赖

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
    </dependency>
</dependencies>
```

