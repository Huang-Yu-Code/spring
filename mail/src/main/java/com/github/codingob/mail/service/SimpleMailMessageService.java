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
