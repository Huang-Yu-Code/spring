package com.github.codingob.mail;

import com.github.codingob.mail.config.AppConfig;
import com.github.codingob.mail.service.MimeMessageHelperService;
import com.github.codingob.mail.service.SimpleMailMessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class MailApplication {
    public static void main(String[] args) {
        String mail = "13713507941@163.com";
        String subject = "测试主题";
        String text = "测试正文";
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
        SimpleMailMessageService simpleMailMessageService = (SimpleMailMessageService) applicationContext.getBean("simpleMailMessageService");
        simpleMailMessageService.sendMail(mail, subject, text);
        MimeMessageHelperService mimeMessageHelperService = (MimeMessageHelperService) applicationContext.getBean("mimeMessageHelperService");
        mimeMessageHelperService.sendMail(mail, subject, text);
        mimeMessageHelperService.sendMail(mail, subject, text, "mail.png");
    }

}
