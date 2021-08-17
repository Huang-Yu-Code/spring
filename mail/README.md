# Spring Mail

依赖

```xml

<dependencies>
    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>javax.mail</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId>
    </dependency>
</dependencies>
```

mail.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:mail.properties"/>

    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
        <property name="host" value="${mail.smtp.host}"/>
        <property name="port" value="${mail.smtp.port}"/>
        <property name="username" value="${mail.smtp.username}"/>
        <property name="password" value="${mail.smtp.password}"/>
    </bean>

    <bean id="simpleMailMessage" class="org.springframework.mail.SimpleMailMessage">
        <property name="from" value="${mail.smtp.username}"/>
    </bean>

    <context:annotation-config/>

</beans>
```

mail.properties

```properties
mail.smtp.host=smtp.qq.com
mail.smtp.port=587
mail.smtp.username=coderfast@qq.com
mail.smtp.password=xxx
```

SimpleMailMessage
```java
package com.github.codingob.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class SimpleMailMessageService {

    private JavaMailSender javaMailSender;
    private SimpleMailMessage templateMessage;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }

    @Autowired
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public void sendMail(String mail, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage(this.templateMessage);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        try{
            this.javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

MimeMessageHelper

```java
package com.github.codingob.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
@PropertySource("classpath:mail.properties")
public class MimeMessageHelperService {
    @Value("${mail.smtp.username}")
    private String username;

    private JavaMailSender javaMailSender;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }

    public void sendMail(String mail, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMail(String mail, String subject, String text, String file) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            ClassPathResource classPathResource = new ClassPathResource(file);
            mimeMessageHelper.addAttachment("mail.png", classPathResource.getFile());
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```


