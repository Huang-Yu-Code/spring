package com.github.codingob.session.websocket.controller;

import com.github.codingob.session.websocket.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Controller
public class SessionController {

    private SessionService service;

    @Autowired
    public void setService(SessionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @MessageMapping("/spring")
    @SendTo("/topic/spring")
    public String response(String message) {
        System.out.println(message);
        return "Hello World";
    }

    @GetMapping("/h")
    @ResponseBody
    public String test() {
        return "ok";
    }

    @GetMapping("/get")
    @ResponseBody
    public String get(HttpServletRequest request) {
        return "Get session username is: " + service.getUsername(request.getSession());
    }

    @GetMapping("/set")
    @ResponseBody
    public String set(HttpServletRequest request) {
        service.setUsername(request.getSession());
        return "Set session username ok!";
    }

}
