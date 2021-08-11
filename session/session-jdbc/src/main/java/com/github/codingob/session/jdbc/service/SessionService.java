package com.github.codingob.session.jdbc.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Service
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class SessionService {
    public String getUsername(HttpSession session) {
        return (String) session.getAttribute("username");
    }

    public void setUsername(HttpSession session) {
        session.setAttribute("username", "spring-session");
    }
}
