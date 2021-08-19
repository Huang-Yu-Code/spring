# Spring Shiro

## 独立应用

依赖

```xml

<dependencies>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
    </dependency>
</dependencies>
```

realm

```java
package com.github.codingob.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class ShiroRealm extends AuthorizingRealm {
    private final static Map<String, String> ACCOUNTS = new ConcurrentHashMap<>();
    private final static Map<String, Set<String>> ROLES = new ConcurrentHashMap<>();
    private final static Map<String, Set<String>> PERMISSIONS = new ConcurrentHashMap<>();

    static {
        ACCOUNTS.put("guest", new Md5Hash("guest", "XXX", 1024).toHex());
        ACCOUNTS.put("admin", new Md5Hash("admin", "XXX", 1024).toHex());
        CopyOnWriteArraySet<String> guestRoles = new CopyOnWriteArraySet<>();
        guestRoles.add("guest");
        ROLES.put("guest", guestRoles);

        CopyOnWriteArraySet<String> adminRoles = new CopyOnWriteArraySet<>();
        adminRoles.add("guest");
        adminRoles.add("admin");
        ROLES.put("admin", adminRoles);

        CopyOnWriteArraySet<String> guestPermissions = new CopyOnWriteArraySet<>();
        guestPermissions.add("guest:create");
        guestPermissions.add("guest:read");
        guestPermissions.add("guest:update");
        guestPermissions.add("guest:delete");
        PERMISSIONS.put("guest", guestPermissions);

        CopyOnWriteArraySet<String> adminPermissions = new CopyOnWriteArraySet<>(guestRoles);
        adminPermissions.add("admin:create");
        adminPermissions.add("admin:read");
        adminPermissions.add("admin:update");
        adminPermissions.add("admin:delete");
        PERMISSIONS.put("admin", adminPermissions);
    }

    public ShiroRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
        credentialsMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // TODO 从数据库读取身份/权限
        authorizationInfo.setRoles(ROLES.get(principalCollection.getPrimaryPrincipal().toString()));
        authorizationInfo.setStringPermissions(PERMISSIONS.get(principalCollection.getPrimaryPrincipal().toString()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // TODO 从数据库对比登录信息
        return new SimpleAuthenticationInfo(
                authenticationToken.getPrincipal(),
                ACCOUNTS.get(authenticationToken.getPrincipal().toString()),
                ByteSource.Util.bytes("XXX"),
                this.getName()
        );
    }
}

```

shiro.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="shiroRealm" class="com.github.codingob.shiro.realm.ShiroRealm"/>

    <bean id="securityManager" class="org.apache.shiro.mgt.DefaultSecurityManager">
        <property name="realm" ref="shiroRealm"/>
    </bean>

    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <bean class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
        <property name="staticMethod" value="org.apache.shiro.SecurityUtils.setSecurityManager"/>
        <property name="arguments" ref="securityManager"/>
    </bean>
</beans>
```

测试

```java
package com.github.codingob.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.PostConstruct;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@SpringJUnitConfig(locations = "classpath:context.xml")
public class ShiroTest {
    @Autowired
    private SecurityManager securityManager;

    @PostConstruct
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    void test() {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        System.out.println(principal);
        UsernamePasswordToken token = new UsernamePasswordToken("guest", "guest");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        System.out.println(subject.getPrincipal());
        System.out.println(subject.hasRole("guest"));
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.isPermitted("guest:create"));
    }

}

```

## Web应用

依赖

```xml

<dependencies>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:context.xml</param-value>
    </context-param>

    <!--  DispatchServlet分发器  -->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value></param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>

    <!--  乱码过滤  -->
    <filter>
        <filter-name>EncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>EncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

context.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <import resource="mvc.xml"/>
    <import resource="shiro.xml"/>

    <context:component-scan base-package="com.github.coding.shiro"/>
</beans>
```

mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--视图解析-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/static/html/"/>
        <property name="suffix" value=".html"/>
    </bean>

    <mvc:annotation-driven/>

    <mvc:default-servlet-handler/>

    <!--静态资源-->
    <mvc:resources mapping="/**" location="classpath:static"/>

</beans>
```

shiro.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="filterChainDefinitions">
            <value>
                / = anon
                /logon = anon
                /login = anon
                /code/** = anon
                /static/** = anon
            </value>
        </property>
    </bean>

    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="realm" ref="shiroRealm"/>
    </bean>

    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <bean id="shiroRealm" class="com.github.codingob.shiro.realm.ShiroRealm"/>

    <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
          depends-on="lifecycleBeanPostProcessor"/>

    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>

</beans>
```

realm

```java
package com.github.coding.shiro.realm;

import AuthService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class ShiroRealm extends AuthorizingRealm {

    public ShiroRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
        credentialsMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // TODO 从数据库读取身份/权限
        simpleAuthorizationInfo.setRoles(AuthService.ROLES);
        simpleAuthorizationInfo.setStringPermissions(AuthService.PERMISSIONS);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        // TODO 从数据库对比登录信息
        return new SimpleAuthenticationInfo(
                principal,
                AuthService.ACCOUNTS.get(principal),
                ByteSource.Util.bytes("XXX"),
                this.getName());
    }
}

```