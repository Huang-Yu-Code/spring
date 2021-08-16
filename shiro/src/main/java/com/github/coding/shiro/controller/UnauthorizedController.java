package com.github.coding.shiro.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 无访问权限
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@ControllerAdvice
public class UnauthorizedController {
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(Exception ex) {
        return "无访问权限";
    }
}
