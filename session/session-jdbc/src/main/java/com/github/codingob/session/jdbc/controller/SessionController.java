package com.github.codingob.session.jdbc.controller;

import com.github.codingob.session.jdbc.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestController
public class SessionController {
    private SessionService service;

    @Autowired
    public void setService(SessionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String get(HttpServletRequest request) {
        return "Get session username is: " + service.getUsername(request.getSession());
    }

    @GetMapping("/set")
    public String set(HttpServletRequest request) {
        service.setUsername(request.getSession());
        return "Set session username ok!";
    }
}
