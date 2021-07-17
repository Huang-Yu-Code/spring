package com.demo.spring.mvc.service;

import com.demo.spring.mvc.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codingob
 */
@Service
public class WebMvcService {

    public boolean login(HttpServletRequest request, String username, String password){
        User user = new User();
        if (user.getUsername().equals(username)){
            if (user.getPassword().equals(password)){
                request.getSession().setAttribute("username", username);
                return true;
            }
        }
        return false;
    }

    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }
}
